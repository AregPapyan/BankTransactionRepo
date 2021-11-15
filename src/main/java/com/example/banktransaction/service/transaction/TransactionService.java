package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;

import java.util.List;

public interface TransactionService {
    List<TransactionUserResponseModel> getAllByUserId(Long id);
    TransactionUserResponseModel add(TransactionUserRequestModel request);
}
