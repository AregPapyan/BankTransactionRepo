package com.example.banktransaction.service.transaction;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import javassist.tools.web.BadHttpRequest;

import java.util.List;

public interface TransactionService {
    List<TransactionUserResponseModel> getAllByUserId(Long id);
    TransactionUserResponseModel add(TransactionUserRequestModel request);
    List<TransactionAdminModel> getAllOrdered();
    List<TransactionAdminModel> getAll();
    TransactionAdminModel accept(Long id);
    TransactionAdminModel reject(Long id);
}
