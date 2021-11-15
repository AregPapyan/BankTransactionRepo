package com.example.banktransaction.service.user;

import com.example.banktransaction.controller.dto.user.UserAdminModel;
import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.persistence.user.User;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<UserAdminModel> getAll();
    UserResponseModel get(Long id);
    UserResponseModel add(UserRequestModel request);
    User findByEmail(String email);
    Long getIdByAuthentication(Authentication authentication);
}
