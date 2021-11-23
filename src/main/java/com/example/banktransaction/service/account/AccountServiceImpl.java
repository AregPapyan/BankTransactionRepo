package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.converter.AccountConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.Account;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountConverter accountConverter;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountConverter = accountConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountAdminModel> getAll() {
        List<Account> requests = accountRepository.findAll();
        return accountConverter.accountsToAdminModels(requests);
    }

    @Override
    @Transactional
    public AccountUserResponseModel add(AccountUserRequestModel request) {
        Account adding = accountConverter.requestToAccount(request);
        Date now = new Date();
        adding.setDateCreated(now);
        adding.setLastUpdated(now);
        adding.setStatus(Status.PENDING);
        adding.setUser(userRepository.getById(request.getUser_id()));
        Account added = accountRepository.save(adding);
        return accountConverter.accountToResponse(added);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountUserResponseModel> getAllByUserId(Long id){
        List<Account> allByUserId = accountRepository.getAllByUserId(id);
        return accountConverter.accountsToResponses(allByUserId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<AccountAdminModel> getUserAccounts(Long id){
        List<Account> allByUserId = accountRepository.getAllByUserId(id);
        return accountConverter.accountsToAdminModels(allByUserId);
    }
    @Override
    @Transactional
    public AccountAdminModel accept(Long id){
        Account byId = accountRepository.getById(id);
        byId.setStatus(Status.ACCEPTED);
        byId.setLastUpdated(new Date());
        return accountConverter.accountToAdminModel(accountRepository.save(byId));
    }
    @Override
    @Transactional
    public AccountAdminModel reject(Long id){
        Account byId = accountRepository.getById(id);
        byId.setStatus(Status.REJECTED);
        byId.setLastUpdated(new Date());
        return accountConverter.accountToAdminModel(accountRepository.save(byId));
    }
//    @Override
//    @Transactional(readOnly = true)
//    public AccountUserResponseModel getAccountByNumber(String number){
//        Account accountByNumber = accountRepository.getAccountByNumber(number);
//        return accountConverter.accountToResponse(accountByNumber);
//    }
}
