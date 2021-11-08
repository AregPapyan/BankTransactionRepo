package com.example.banktransaction.controller;

import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRequestModel userRequestModel(){
        return new UserRequestModel();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "registration.html";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")UserRequestModel registrationDto){
     //userService.save(registrationDto);
     return "redirect:/registration?success";
    }
}
