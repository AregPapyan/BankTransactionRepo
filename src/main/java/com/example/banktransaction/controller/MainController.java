package com.example.banktransaction.controller;

import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login.html";
    }

    @PostMapping("/home")
    public String userIndex(User user) throws NotFoundException {
      userService.login(user.getEmail(),user.getPassword());
        return "home-page.html";
    }

    @GetMapping("/nothing")
    public void doNothing( Authentication authentication){
        System.out.println(authentication.getName());
    }

    @GetMapping("/log-out")
    public String logOut(){

        return"login.html";
    }
}
