package com.example.banktransaction.converter;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.persistence.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionConverter {
    private final AccountConverter accountConverter;

    public TransactionConverter(AccountConverter accountConverter) {
        this.accountConverter = accountConverter;
    }

    public List<TransactionAdminModel> transactionsToAdminModels(List<Transaction> transactions){
        List<TransactionAdminModel> adminModels = new ArrayList<>();
        for(Transaction transaction:transactions){
            adminModels.add(transactionToAdminModel(transaction));
        }
        return adminModels;
    }
    public TransactionAdminModel transactionToAdminModel(Transaction transaction){
        TransactionAdminModel adminModel = new TransactionAdminModel();
        adminModel.setId(transaction.getId());
        adminModel.setType(transaction.getType());
        adminModel.setAmount(transaction.getAmount());
        adminModel.setFrom(accountConverter.accountToAdminModel(transaction.getFrom()));
        adminModel.setTo(accountConverter.accountToAdminModel(transaction.getTo()));
        adminModel.setDateCreated(transaction.getDateCreated());
        adminModel.setLastUpdated(transaction.getLastUpdated());
        adminModel.setStatus(transaction.getStatus());
        return adminModel;
    }
    public TransactionUserResponseModel transactionToResponse(Transaction transaction){
        TransactionUserResponseModel response = new TransactionUserResponseModel();
        response.setId(transaction.getId());
        response.setType(transaction.getType());
        response.setAmount(transaction.getAmount());
        response.setFrom(accountConverter.accountToResponse(transaction.getFrom()));
        response.setTo(accountConverter.accountToResponse(transaction.getTo()));
        response.setDateCreated(transaction.getDateCreated());
        response.setLastUpdated(transaction.getLastUpdated());
        response.setStatus(transaction.getStatus());
        return response;
    }
    public List<TransactionUserResponseModel> transactionsToResponses(List<Transaction> transactions){
        List<TransactionUserResponseModel> responses = new ArrayList<>();
        for(Transaction transaction:transactions){
            responses.add(transactionToResponse(transaction));
        }
        return responses;
    }
    public Transaction requestToTransaction(TransactionUserRequestModel request){
        Transaction transaction = new Transaction();
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        return transaction;
    }
}
