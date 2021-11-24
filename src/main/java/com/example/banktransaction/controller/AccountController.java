package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.controller.dto.account.AccountUserRequestModel;
import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.service.account.AccountService;
import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/account")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<AccountAdminModel>> getRequests(){
        return ResponseEntity.ok(accountService.getAll());
    }
    @GetMapping("/accounts/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<AccountAdminModel>> getUserAccounts(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getUserAccounts(id));
    }
    @GetMapping("/user/account")
    public ResponseEntity<List<AccountUserResponseModel>> getAllByUserId(Authentication authentication){
        Long id = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(accountService.getAllByUserId(id));
    }
    @PostMapping("/account")
    public ResponseEntity<AccountUserResponseModel> add(@RequestBody AccountUserRequestModel request,Authentication authentication){
        Long userId = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(accountService.add(request, userId));
    }
    @PutMapping("/account/accept/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<AccountAdminModel> accept(@PathVariable Long id){
        return ResponseEntity.ok(accountService.accept(id));
    }
    @PutMapping("/account/reject/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<AccountAdminModel> reject(@PathVariable Long id){
        return ResponseEntity.ok(accountService.reject(id));
    }

    @PutMapping("/update-account/{number}")
    public ResponseEntity<AccountUserResponseModel> updateAccount(@RequestBody AccountUserRequestModel accountUserRequestModel, @PathVariable String number, Authentication authentication) throws NotFoundException {
      Long userId = userService.getIdByAuthentication(authentication);
       return ResponseEntity.ok(accountService.updateAccount(accountUserRequestModel, number, userId));
    }

//    @DeleteMapping
//    public void deleteAccount(@PathVariable Long id, Authentication authentication){
//        accountService.deleteAccount(id, authentication);
//    }

}
