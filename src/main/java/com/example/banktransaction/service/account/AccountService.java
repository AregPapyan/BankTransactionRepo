package com.example.banktransaction.service.account;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountAdminModel> getAll();
    //List<AccountAdminModel> getRequests();
    AccountUserResponseModel add(AccountUserRequestModel request, Long userId);
    List<AccountUserResponseModel> getAllByUserId(Long id);
    List<AccountAdminModel> getUserAccounts(Long id);
    AccountAdminModel accept(Long id);
    AccountAdminModel reject(Long id);
    AccountUserResponseModel updateAccount(AccountUserRequestModel accountUserRequestModel,String number, Long userId) throws NotFoundException;
    AccountUserResponseModel deActivate(Long id, Long userId) throws NotFoundException;
    AccountUserResponseModel activate(Long id, Long userId) throws NotFoundException;
}
