package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.persistence.account.Account;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;

import java.rmi.activation.ActivationException;
import java.util.List;

public interface AccountService {
    List<AccountAdminModel> getAll();
    AccountUserResponseModel add(AccountUserRequestModel request, Long userId);
    List<AccountUserResponseModel> getAllByUserId(Long id);
    List<AccountUserResponseModel> getValidByUserId(Long user_id);
    List<AccountAdminModel> getUserAccounts(Long id);
    AccountAdminModel accept(Long id);
    AccountAdminModel reject(Long id);
    AccountUserResponseModel updateAccount(AccountUserRequestModel accountUserRequestModel,String number, Long userId) throws NotFoundException;
    AccountUserResponseModel deActivate(Long id, Long userId) throws  ActivationException;
    AccountUserResponseModel activate(Long id, Long userId) throws NotFoundException;
}
