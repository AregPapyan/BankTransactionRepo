package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.converter.TransactionConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.transaction.Transaction;
import com.example.banktransaction.persistence.transaction.TransactionRepository;
import javassist.tools.web.BadHttpRequest;
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
    public List<TransactionAdminModel> getAllOrdered(){
        List<Transaction> allGrouped = transactionRepository.getAllOrdered();
        return transactionConverter.transactionsToAdminModels(allGrouped);
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
    public TransactionUserResponseModel add(TransactionUserRequestModel request){
//        if(accountRepository.getAccountByNumber(request.getFrom()).getStatus()!=Status.ACCEPTED
//                ||
//        accountRepository.getAccountByNumber(request.getTo()).getStatus()!=Status.ACCEPTED){
//            throw new BadHttpRequest();
//        }
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
}
