package com.example.banktransaction.service;

import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.converter.UserConverter;
import com.example.banktransaction.persistence.user.User;
import com.example.banktransaction.persistence.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
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
        User added = userRepository.save(adding);
        return userConverter.userToResponse(added);
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
