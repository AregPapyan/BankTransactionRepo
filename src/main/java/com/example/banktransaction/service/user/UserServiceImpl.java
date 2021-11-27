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
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        adding.setActive(true);
        User added = userRepository.save(adding);
        return userConverter.userToResponse(added);
    }


    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserResponseModel update(UserRequestModel request, Long id){
        User updating = userRepository.getById(id);
        Date now = new Date();
        updating.setFirstName(request.getFirstName());
        updating.setLastName(request.getLastName());
        updating.setEmail(request.getEmail());
        updating.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        updating.setBirthDate(request.getBirthDate());
        updating.setMobile(request.getMobile());
        updating.getAddress().setCountry(request.getAddressRequest().getCountry());
        updating.getAddress().setCity(request.getAddressRequest().getCity());
        updating.getAddress().setStreet(request.getAddressRequest().getStreet());
        updating.getAddress().setHouseNumber(request.getAddressRequest().getHouseNumber());
        updating.getAddress().setPostalCode(request.getAddressRequest().getPostalCode());
        updating.setLastUpdated(now);
        User updated = userRepository.save(updating);
        return userConverter.userToResponse(updated);
    }

    @Override
    @Transactional
    public UserAdminModel deactivate(Long id) {
        User byId = userRepository.getById(id);
        byId.setActive(false);
        byId.setLastUpdated(new Date());
        return userConverter.userToAdminModel(userRepository.save(byId));
    }

    @Override
    @Transactional
    public UserAdminModel activate(Long id) {
        User byId = userRepository.getById(id);
        byId.setActive(true);
        byId.setLastUpdated(new Date());
        return userConverter.userToAdminModel(userRepository.save(byId));
    }
    @Override
    public Long getIdByAuthentication(Authentication authentication) {
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        return userDetails.getId();
    }
}
