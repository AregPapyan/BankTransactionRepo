package com.example.banktransaction.service.user;

import com.example.banktransaction.config.CustomUserDetail;
import com.example.banktransaction.controller.dto.user.UserAdminModel;
import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.converter.UserConverter;
import com.example.banktransaction.persistence.authority.Authority;
import com.example.banktransaction.persistence.authority.AuthorityRepository;
import com.example.banktransaction.persistence.authority.AuthorityType;
import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.persistence.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserConverter userConverter;



    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAdminModel> getAll() {
        List<User> all = userRepository.findAll();
        return userConverter.usersToAdminModels(all);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseModel get(Long id) {
        User byId = userRepository.getById(id);
        return userConverter.userToResponse(byId);
    }

    @Override
    @Transactional
    public UserResponseModel add(UserRequestModel request) {
        User adding = userConverter.requestToUser(request);
        Date now = new Date();
        adding.setDateCreated(now);
        adding.setLastUpdated(now);
        adding.getAddress().setDateCreated(now);
        adding.getAddress().setLastUpdated(now);
        Set<Authority> authorities = new HashSet<>();
        Authority byName = authorityRepository.getByName(AuthorityType.USER);
        authorities.add(byName);
        adding.setAuthorities(authorities);
        User added = userRepository.save(adding);
        return userConverter.userToResponse(added);
    }


    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Long getIdByAuthentication(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        return userDetails.getId();
    }

}
