package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.persistence.transaction.Transaction;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    List<TransactionUserResponseModel> getAllByUserId(Long id);
    TransactionUserResponseModel add(TransactionUserRequestModel request);
    void updateTransaction(Long id, TransactionUserRequestModel transactionUserRequestModel, Authentication authentication);
    Transaction getById(Long id);
}
