package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.converter.TransactionConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.transaction.Transaction;
import com.example.banktransaction.persistence.transaction.TransactionRepository;

import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final AccountRepository accountRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionConverter transactionConverter, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionUserResponseModel> getAllByUserId(Long id) {
        List<Transaction> allByUserId = transactionRepository.getAllByUserId(id);
        return transactionConverter.transactionsToResponses(allByUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionAdminModel> getAll(){
        List<Transaction> all = transactionRepository.findAll();
        return transactionConverter.transactionsToAdminModels(all);
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
        byId.setActive(false);
        return transactionConverter.transactionToAdminModel(transactionRepository.save(byId));
    }

    @Override
    @Transactional
    public TransactionUserResponseModel add(TransactionUserRequestModel request, Long userId) throws NotFoundException {
        if(accountRepository.getAccountByNumber(request.getFrom()).getUser().getId()!=userId){
            throw new NotFoundException("You can use only your accounts");
        }
        else if(accountRepository.getAccountByNumber(request.getFrom()).getStatus()!=Status.ACCEPTED
                || accountRepository.getAccountByNumber(request.getTo()).getStatus()!=Status.ACCEPTED
        || !accountRepository.getAccountByNumber(request.getFrom()).isActive()
                || !accountRepository.getAccountByNumber(request.getTo()).isActive()){
            throw new NotFoundException("You can use only active accepted accounts");
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
    public TransactionUserResponseModel update(Long id, TransactionUserRequestModel transactionUserRequestModel, Long userId) throws NotFoundException {
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
                    throw new NotFoundException("You can use only accepted accounts");
                }

            }
            else{
                throw new NotFoundException("You can update only  transactions with PENDING status");
            }
        }
        else{
            throw new NotFoundException("You can update only your transactions");
        }
    }

    @Override
    @Transactional
    public TransactionUserResponseModel deActivate(Long id, Long userId) throws NotFoundException {
        Transaction transaction = transactionRepository.getById(id);
        if(!transaction.isActive()){
            throw new NotFoundException("Already inactive");
        }
        if(transaction.getStatus().equals(Status.ACCEPTED) || transaction.getStatus().equals(Status.REJECTED)){
            throw new NotFoundException("You can deActive only  transactions with PENDING status");
        }
        if(transaction.getFrom().getUser().getId() != userId){
            throw new NotFoundException("You can delete only your transactions");
        }else{
            transaction.setActive(false);
            transaction.setLastUpdated(new Date());
        }
        return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
    }

    @Override
    @Transactional
    public TransactionUserResponseModel activate(Long id, Long userId) throws NotFoundException {
        Transaction transaction = transactionRepository.getById(id);
        if(transaction.isActive()){
            throw new NotFoundException("Already Active");
        }
        if(transaction.getFrom().getUser().getId() != userId){
            throw new NotFoundException("You can activate only your transactions");
        }else{
            transaction.setActive(true);
            transaction.setLastUpdated(new Date());
        }
        return transactionConverter.transactionToResponse(transactionRepository.save(transaction));
    }

}
