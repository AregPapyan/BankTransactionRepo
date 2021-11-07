package com.example.banktransaction.service;

import com.example.banktransaction.model.User;
import com.example.banktransaction.web.dto.UserRegistrationDto;

public interface UserService {
    User save(UserRegistrationDto registrationDto);
}
