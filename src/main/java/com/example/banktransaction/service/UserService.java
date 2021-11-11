package com.example.banktransaction.service;

import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.persistence.user.User;
import javassist.NotFoundException;

import java.util.List;

public interface UserService {
    //User save(UserRegistrationDto registrationDto);
    List<UserResponseModel> getAll();
    UserResponseModel get(Long id);
    UserResponseModel add(UserRequestModel request);
    void login (String email, String password) throws NotFoundException;
    User getByEmail(String email);
}
