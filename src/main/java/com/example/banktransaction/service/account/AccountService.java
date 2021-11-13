package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;

import java.util.List;

public interface AccountService {
    List<AccountAdminModel> getRequests();
}
