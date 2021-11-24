package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.persistence.transaction.Transaction;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TransactionService {
    List<TransactionUserResponseModel> getAllByUserId(Long id);
    List<TransactionAdminModel> getAllOrdered();
    List<TransactionAdminModel> getAll();
    TransactionAdminModel accept(Long id);
    TransactionAdminModel reject(Long id);
    TransactionUserResponseModel add(TransactionUserRequestModel request, Long userId) throws NotFoundException;
    TransactionUserResponseModel update(Long id, TransactionUserRequestModel transactionUserRequestModel, Long userId) throws NotFoundException;

    //void deleteTransaction(Long id, Authentication authentication);
}
