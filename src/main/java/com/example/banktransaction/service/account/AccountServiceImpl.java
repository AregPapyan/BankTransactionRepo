package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.converter.AccountConverter;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.Account;
import com.example.banktransaction.persistence.account.AccountRepository;
import com.example.banktransaction.persistence.authority.Authority;
import com.example.banktransaction.persistence.authority.AuthorityType;
import com.example.banktransaction.persistence.user.UserRepository;
import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<AccountAdminModel> getAll() {
        List<Account> requests = accountRepository.findAll();
        return accountConverter.accountsToAdminModels(requests);
    }

    @Override
    @Transactional
    public AccountUserResponseModel add(AccountUserRequestModel request, Long userId) {
        Account adding = accountConverter.requestToAccount(request);
        adding.setActive(true);
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
    @Transactional(readOnly = true)
    public List<AccountAdminModel> getUserAccounts(Long id){
        List<Account> allByUserId = accountRepository.getAllByUserId(id);
        return accountConverter.accountsToAdminModels(allByUserId);
    }
    @Override
    public List<AccountUserResponseModel> getValidByUserId(Long user_id){
        List<Account> validByUserId = accountRepository.getValidByUserId(user_id);
        return accountConverter.accountsToResponses(validByUserId);
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
        byId.setActive(false);
        return accountConverter.accountToAdminModel(accountRepository.save(byId));
    }

  @Override
  @Transactional
    public AccountUserResponseModel updateAccount(AccountUserRequestModel accountUserRequestModel, String number, Long userId) throws NotFoundException {
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

   @Override
   @Transactional
    public AccountUserResponseModel deActivate(Long id, Long userId) throws NotFoundException {
        Account account = accountRepository.getById(id);
        if(!account.isActive()){
            throw new NotFoundException("Already InActive ! ");
        }
        if(userRepository.getById(userId).getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()).contains(AuthorityType.ADMIN)
                && account.getStatus().equals(Status.ACCEPTED)){
            account.setActive(false);
            account.setLastUpdated(new Date());
        }else if(userRepository.getById(userId).getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()).contains(AuthorityType.USER)
                && account.getStatus().equals(Status.PENDING)
                && account.getUser().getId() == userId){
            account.setActive(false);
            account.setLastUpdated(new Date());
        }
        else{
            throw new NotFoundException("You can't deActive this transactions");
        }
        return accountConverter.accountToResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountUserResponseModel activate(Long id, Long userId) throws NotFoundException {
        Account account = accountRepository.getById(id);
        if(account.isActive()){
            throw new NotFoundException("Already Active !!");
        }
        if(userRepository.getById(userId).getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()).contains(AuthorityType.ADMIN)
                && account.getStatus().equals(Status.ACCEPTED)){
            account.setActive(true);
            account.setLastUpdated(new Date());
        }else if(userRepository.getById(userId).getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()).contains(AuthorityType.USER)
                && account.getStatus().equals(Status.PENDING) && account.getUser().getId() == userId){
            account.setActive(true);
            account.setLastUpdated(new Date());
        }

        return accountConverter.accountToResponse(accountRepository.save(account));

    }
}
