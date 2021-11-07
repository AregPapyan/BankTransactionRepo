package com.example.banktransaction.service;

import com.example.banktransaction.model.Authority;
import com.example.banktransaction.model.User;
import com.example.banktransaction.repository.AuthorityRepository;
import com.example.banktransaction.repository.UserRepository;
import com.example.banktransaction.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
   private UserRepository userRepository;

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),registrationDto.getEmail(), registrationDto.getPassword());
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.getByName("ROLE_USER"));
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }
}
