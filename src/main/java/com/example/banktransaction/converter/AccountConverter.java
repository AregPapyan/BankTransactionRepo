package com.example.banktransaction.converter;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.persistence.account.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountConverter {
    private final UserConverter userConverter;

    public AccountConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public AccountAdminModel accountToAdminModel(Account account){
        AccountAdminModel adminModel = new AccountAdminModel();
        adminModel.setId(account.getId());
        adminModel.setNumber(account.getNumber());
        adminModel.setCurrency(account.getCurrency());
        adminModel.setDateCreated(account.getDateCreated());
        adminModel.setLastUpdated(account.getLastUpdated());
        adminModel.setStatus(account.getStatus());
        adminModel.setUserAdminModel(userConverter.userToAdminModel(account.getUser()));
        return adminModel;
    }
    public List<AccountAdminModel> accountsToAdminModels(List<Account> accounts){
        List<AccountAdminModel> adminModels = new ArrayList<>();
        for (Account account:accounts){
            adminModels.add(accountToAdminModel(account));
        }
        return adminModels;
    }
}
