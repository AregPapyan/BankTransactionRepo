package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.service.transaction.TransactionService;
import com.example.banktransaction.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }
    @GetMapping("/user/transaction")
    public ResponseEntity<List<TransactionUserResponseModel>> getAllByUserId(Authentication authentication){
        Long id = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(transactionService.getAllByUserId(id));
    }
    @PostMapping("/transaction")
    public ResponseEntity<TransactionUserResponseModel> add(@RequestBody TransactionUserRequestModel request){
        return ResponseEntity.ok(transactionService.add(request));
    }
}
