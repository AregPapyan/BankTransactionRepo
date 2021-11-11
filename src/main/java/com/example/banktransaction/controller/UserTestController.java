package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserTestController {
    private final UserService userService;

    public UserTestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseModel>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseModel> get(@PathVariable Long id){
        return ResponseEntity.ok(userService.get(id));
    }
    @PostMapping
    public ResponseEntity<UserResponseModel> add(@RequestBody UserRequestModel request){
        return ResponseEntity.ok(userService.add(request));
    }
}
