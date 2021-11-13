package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.service.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<AccountAdminModel>> getRequests(Authentication authentication){
        return ResponseEntity.ok(accountService.getRequests());
    }
}
