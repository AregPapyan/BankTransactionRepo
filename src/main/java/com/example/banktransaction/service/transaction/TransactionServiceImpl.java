package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.converter.TransactionConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.transaction.Transaction;
import com.example.banktransaction.persistence.transaction.TransactionRepository;
import com.example.banktransaction.service.user.UserService;
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
    public TransactionUserResponseModel add(TransactionUserRequestModel request) {
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
    public void updateTransaction(Long id, TransactionUserRequestModel transactionUserRequestModel, Authentication authentication) {
        if(userService.getById(userService.getIdByAuthentication(authentication)).equals(userService.findByEmail(transactionUserRequestModel.getFrom()))){
            Transaction transaction = getById(id);
            /*transaction = transactionConverter.requestToTransaction(transactionUserRequestModel);*/
            System.out.println(transaction);
            if(transaction.getStatus().toString().equals("PENDING")){
                transaction.setAmount(transactionUserRequestModel.getAmount());
                transaction.setType(transactionUserRequestModel.getType());
                transaction.setFrom(accountRepository.getAccountByNumber(transactionUserRequestModel.getFrom()));
                transaction.setTo(accountRepository.getAccountByNumber(transactionUserRequestModel.getTo()));
                Date now = new Date();
                transaction.setLastUpdated(now);
                transactionRepository.save(transaction);
            }
        }
    }

    @Override
    public Transaction getById(Long id) {
        return transactionRepository.getById(id);
    }
}
