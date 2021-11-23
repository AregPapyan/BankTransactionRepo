package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.converter.AccountConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.Account;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.user.UserRepository;
import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountConverter accountConverter;
    private final UserService userService;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, AccountConverter accountConverter, UserService userService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountConverter = accountConverter;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountAdminModel> getRequests() {
        List<Account> requests = accountRepository.getRequests();
        return accountConverter.accountsToAdminModels(requests);
    }

    @Override
    @Transactional
    public AccountUserResponseModel add(AccountUserRequestModel request, Long userId) {
        Account adding = accountConverter.requestToAccount(request);
        Date now = new Date();
        adding.setDateCreated(now);
        adding.setLastUpdated(now);
        adding.setStatus(Status.PENDING);
        adding.setUser(userRepository.getById(userId));
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
    @Transactional
    public AccountAdminModel accept(Long id){
        Account byId = accountRepository.getById(id);
        byId.setStatus(Status.ACCEPTED);
        return accountConverter.accountToAdminModel(accountRepository.save(byId));
    }
    @Override
    @Transactional
    public AccountAdminModel reject(Long id){
        Account byId = accountRepository.getById(id);
        byId.setStatus(Status.REJECTED);
        return accountConverter.accountToAdminModel(accountRepository.save(byId));
    }

    @Override
    public AccountUserResponseModel updateAccount(AccountUserRequestModel accountUserRequestModel,String number,  Long userId) throws NotFoundException {
        Account accountByNumber = accountRepository.getAccountByNumber(number);
        if(accountByNumber.getUser().getId()!=userId){
            throw new NotFoundException("You can update only your accounts");
        }else if(accountByNumber.getStatus()!=Status.PENDING){
            throw new NotFoundException("You can't update accepted/rejected account");
        }
        else {
            accountByNumber.setNumber(accountUserRequestModel.getNumber());
            accountByNumber.setCurrency(accountUserRequestModel.getCurrency());
            accountByNumber.setLastUpdated(new Date());
            Account updated = accountRepository.save(accountByNumber);
            return accountConverter.accountToResponse(updated);
        }
    }

//    @Override
//    public void deleteAccount(Long id, Authentication authentication) {
//
//        boolean can = (id == accountRepository.getById(userService.getIdByAuthentication(authentication)).getUser().getId()) ||
//                (accountRepository.getById(userService.getIdByAuthentication(authentication)).getUser().getAuthorities().toString().equals("ADMIN"));
//        if(can){
//            accountRepository.deleteById(id);
//        }else {
//            throw new RuntimeException("You can't delete this account");
//        }
//    }
}
