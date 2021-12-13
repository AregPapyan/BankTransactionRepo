package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;

import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionService {
    List<TransactionUserResponseModel> getAllByUserId(Long id);
    List<TransactionAdminModel> getAll();
    TransactionAdminModel accept(Long id);
    TransactionAdminModel reject(Long id);
    TransactionUserResponseModel add(TransactionUserRequestModel request, Long userId) throws NotFoundException;
    TransactionUserResponseModel update(Long id, TransactionUserRequestModel transactionUserRequestModel, Long userId) throws NotFoundException;
    TransactionUserResponseModel deActivate(Long id, Long userId) throws NotFoundException;
    TransactionUserResponseModel activate(Long id, Long userId) throws NotFoundException;
    //Balance
    List<Double> balance(Long id);
}
