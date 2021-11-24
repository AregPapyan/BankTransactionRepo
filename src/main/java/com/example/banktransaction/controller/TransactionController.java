package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.transaction.TransactionAdminModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserRequestModel;
import com.example.banktransaction.controller.dto.transaction.TransactionUserResponseModel;
import com.example.banktransaction.service.transaction.TransactionService;
import com.example.banktransaction.service.user.UserService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/transaction/accept/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<TransactionAdminModel> accept(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.accept(id));
    }
    @PutMapping("/transaction/reject/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<TransactionAdminModel> reject(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.reject(id));
    }
    @PostMapping("/transaction")
    public ResponseEntity<TransactionUserResponseModel> add(@RequestBody TransactionUserRequestModel request, Authentication authentication) throws NotFoundException {
        Long userId = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(transactionService.add(request, userId));
    }
    @GetMapping("/transaction")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<TransactionAdminModel>> getAllGrouped(){
        return ResponseEntity.ok(transactionService.getAllOrdered());
    }
    @GetMapping("/transaction/all")
    public ResponseEntity<List<TransactionAdminModel>> getAll(){
        return ResponseEntity.ok(transactionService.getAll());
    }

    @PutMapping("/updateTransaction/{id}")
    public ResponseEntity<TransactionUserResponseModel> update(@PathVariable Long id, @RequestBody TransactionUserRequestModel transactionUserRequestModel, Authentication authentication) throws NotFoundException {
        Long userId = userService.getIdByAuthentication(authentication);
        return ResponseEntity.ok(transactionService.update(id, transactionUserRequestModel, userId));
    }
//
//   @DeleteMapping
//    public void deleteTransaction(@PathVariable Long id, Authentication authentication){
//      transactionService.deleteTransaction(id, authentication);
//   }
}
