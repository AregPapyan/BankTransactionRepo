package com.example.banktransaction.service;

import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.converter.UserConverter;
import com.example.banktransaction.persistence.authority.Authority;
import com.example.banktransaction.persistence.authority.AuthorityRepository;
import com.example.banktransaction.persistence.authority.AuthorityType;
import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.persistence.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserConverter userConverter;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userConverter = userConverter;
    }

    @Override
    public List<UserResponseModel> getAll() {
        List<User> all = userRepository.findAll();
        return userConverter.usersToResponses(all);
    }

    @Override
    public UserResponseModel get(Long id) {
        User byId = userRepository.getById(id);
        return userConverter.userToResponse(byId);
    }

    @Override
    public UserResponseModel add(UserRequestModel request) {
        User adding = userConverter.requestToUser(request);
        Date now = new Date();
        adding.setDateCreated(now);
        adding.setLastUpdated(now);
        adding.getAddress().setDateCreated(now);
        adding.getAddress().setLastUpdated(now);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityRepository.getByName(AuthorityType.USER));
        adding.setAuthorities(authorities);
        User added = userRepository.save(adding);
        return userConverter.userToResponse(added);
    }

    @Override
    public void login(String email, String password) throws NotFoundException {
        User user = new User();
        user = getByEmail(email);
        if(user == null){
            throw new NotFoundException("Invalid Username");
        }

            boolean isPasswordMatches = passwordEncoder.matches(
                    password,
                    user.getPassword()
            );
        if(!isPasswordMatches){
            throw new NotFoundException("Invalid Username");
        }
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }


//    @Autowired
//    private AuthorityRepository authorityRepository;
//
//    @Autowired
//   private UserRepository userRepository;

//    @Override
//    public User save(UserRegistrationDto registrationDto) {
//        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),registrationDto.getEmail(), registrationDto.getPassword());
//        Set<Authority> authorities = new HashSet<>();
//        authorities.add(authorityRepository.getByName(AuthorityType.USER));
//        user.setAuthorities(authorities);
//        return userRepository.save(user);
//    }
}
