package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.converter.AccountConverter;
import com.example.banktransaction.persistence.account.Account;
import com.example.banktransaction.persistence.account.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountAdminModel> getRequests() {
        List<Account> requests = accountRepository.getRequests();
        return accountConverter.accountsToAdminModels(requests);
    }
}
