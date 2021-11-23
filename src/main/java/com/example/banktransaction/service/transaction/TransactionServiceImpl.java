package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.converter.TransactionConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.transaction.Transaction;
import com.example.banktransaction.persistence.transaction.TransactionRepository;
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
    public List<TransactionUserResponseModel> getAllByUserId(Long id) {
        List<Transaction> allByUserId = transactionRepository.getAllByUserId(id);
        return transactionConverter.transactionsToResponses(allByUserId);
    }

    @Override
    @Transactional
    public TransactionUserResponseModel add(TransactionUserRequestModel request, Long userId) throws NotFoundException {
        if(accountRepository.getAccountByNumber(request.getFrom()).getUser().getId()!=userId){
            throw new NotFoundException("You can use only your accounts");
        }
        else if(accountRepository.getAccountByNumber(request.getFrom()).getStatus()!=Status.ACCEPTED
                || accountRepository.getAccountByNumber(request.getTo()).getStatus()!=Status.ACCEPTED){
            throw new NotFoundException("You can use only accepted accounts");
        }
        Transaction adding = transactionConverter.requestToTransaction(request);
        adding.setFrom(accountRepository.getAccountByNumber(request.getFrom()));
        adding.setTo(accountRepository.getAccountByNumber(request.getTo()));
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
                if(accountRepository.getAccountByNumber(transactionUserRequestModel.getTo()).getStatus() == Status.PENDING){
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




}
