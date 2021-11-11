package com.example.banktransaction.controller;

import com.example.banktransaction.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login-with")
    public void login(@RequestParam("email")  String email, @RequestParam("password")  String password) throws NotFoundException {
       userService.login(email, password);
    }
}
