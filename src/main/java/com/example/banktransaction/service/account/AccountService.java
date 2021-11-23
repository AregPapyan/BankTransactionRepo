package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;

import java.util.List;

public interface AccountService {
    List<AccountAdminModel> getAll();
    AccountUserResponseModel add(AccountUserRequestModel request);
    List<AccountUserResponseModel> getAllByUserId(Long id);
    List<AccountAdminModel> getUserAccounts(Long id);
    AccountAdminModel accept(Long id);
    AccountAdminModel reject(Long id);
}
