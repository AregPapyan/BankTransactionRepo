package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.converter.TransactionConverter;
import com.example.banktransaction.exception.ActivationException;
import com.example.banktransaction.exception.AuthorityException;
import com.example.banktransaction.exception.StatusException;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.transaction.Transaction;
import com.example.banktransaction.persistence.transaction.TransactionRepository;

import javassist.tools.web.BadHttpRequest;
import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final AccountRepository accountRepository;
    private final UserService userService;


    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionConverter transactionConverter, AccountRepository accountRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionAdminModel> getAll(){
        List<Transaction> all = transactionRepository.findAll();
        return transactionConverter.transactionsToAdminModels(all);
    }
    @Override
    @Transactional(readOnly = true)
    public List<TransactionUserResponseModel> getAllByUserId(Long id) {
        List<Transaction> allByUserId = transactionRepository.getAllByUserId(id);
        return transactionConverter.transactionsToResponses(allByUserId);
    }

    @Override
    @Transactional
    public TransactionUserResponseModel add(TransactionUserRequestModel request, Long userId){

 if(accountRepository.getAccountByNumber(request.getFrom()).getUser().getId()!=userId){
            throw new AuthorityException("You can use only your accounts");
        }
        else if(accountRepository.getAccountByNumber(request.getFrom()).getStatus()!=Status.ACCEPTED
                || accountRepository.getAccountByNumber(request.getTo()).getStatus()!=Status.ACCEPTED
        || !accountRepository.getAccountByNumber(request.getFrom()).isActive()
                || !accountRepository.getAccountByNumber(request.getTo()).isActive()){
            throw new StatusException("You can use only active accepted accounts");
        }
        Transaction adding = transactionConverter.requestToTransaction(request);
        adding.setFrom(accountRepository.getAccountByNumber(request.getFrom()));
        adding.setTo(accountRepository.getAccountByNumber(request.getTo()));
        adding.setActive(true);
        Date now = new Date();
        adding.setDateCreated(now);
        adding.setLastUpdated(now);
        adding.setStatus(Status.PENDING);
        return transactionConverter.transactionToResponse(transactionRepository.save(adding));
    }
    @Override
    @Transactional
    public TransactionAdminModel accept(Long id){
        Transaction byId = transactionRepository.getById(id);
        byId.setStatus(Status.ACCEPTED);
        byId.setLastUpdated(new Date());
        return transactionConverter.transactionToAdminModel(transactionRepository.save(byId));
    }
    @Override
    @Transactional
    public TransactionAdminModel reject(Long id){
        Transaction byId = transactionRepository.getById(id);
        byId.setStatus(Status.REJECTED);
        byId.setLastUpdated(new Date());
        return transactionConverter.transactionToAdminModel(transactionRepository.save(byId));
    }

    @Override
    @Transactional
    public TransactionUserResponseModel update(Long id, TransactionUserRequestModel transactionUserRequestModel, Long userId)  {
        if(userId == transactionRepository.getById(id).getFrom().getUser().getId()){

            Transaction transaction = transactionRepository.getById(id);
            if(transaction.getStatus()==Status.PENDING){
                if(accountRepository.getAccountByNumber(transactionUserRequestModel.getTo()).getStatus() == Status.ACCEPTED){
                    transaction.setAmount(transactionUserRequestModel.getAmount());
                    transaction.setType(transactionUserRequestModel.getType());
                    transaction.setTo(accountRepository.getAccountByNumber(transactionUserRequestModel.getTo()));
                    Date now = new Date();
                    transaction.setLastUpdated(now);
                    return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
                }else{
                    throw new StatusException("You can use only accepted accounts");
                }

            }
            else{
                throw new StatusException("You can update only  transactions with PENDING status");
            }
        }
        else{
            throw new AuthorityException("You can update only your transactions");
        }
    }

    @Override
    public TransactionUserResponseModel deActivate(Long id, Long userId)  {
        Transaction transaction = transactionRepository.getById(id);
        if(!transaction.isActive()){
            throw new ActivationException("Already inactive");
        }
        if(transaction.getStatus().equals(Status.ACCEPTED) || transaction.getStatus().equals(Status.REJECTED)){
            throw new StatusException("You can deActive only  transactions with PENDING status");
        }
        if(transaction.getFrom().getUser().getId() != userId){
            throw new AuthorityException("You can delete only your transactions");
        }else{
            transaction.setActive(false);
            transaction.setLastUpdated(new Date());
        }
        return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
    }

    @Override
    public TransactionUserResponseModel activate(Long id, Long userId) {
        Transaction transaction = transactionRepository.getById(id);
        if(transaction.isActive()){
            throw new ActivationException("Already Active");
        }
        if(transaction.getFrom().getUser().getId() != userId){
            throw new AuthorityException("You can activate only your transactions");
        }else{
            transaction.setActive(true);
            transaction.setLastUpdated(new Date());
        }
        return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
    }

}
