package com.example.banktransaction.service;

import com.example.banktransaction.persistence.authority.Authority;
import com.example.banktransaction.persistence.authority.AuthorityRepository;
import com.example.banktransaction.persistence.authority.AuthorityType;
import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.persistence.user.UserRepository;
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

//    @Override
//    public User save(UserRegistrationDto registrationDto) {
//        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),registrationDto.getEmail(), registrationDto.getPassword());
//        Set<Authority> authorities = new HashSet<>();
//        authorities.add(authorityRepository.getByName(AuthorityType.USER));
//        user.setAuthorities(authorities);
//        return userRepository.save(user);
//    }
}
