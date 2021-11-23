package com.example.banktransaction.converter;

import com.example.banktransaction.controller.dto.user.UserAdminModel;
import com.example.banktransaction.controller.dto.user.UserRequestModel;
import com.example.banktransaction.controller.dto.user.UserResponseModel;
import com.example.banktransaction.persistence.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    private final AddressConverter addressConverter;

    public UserConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public UserResponseModel userToResponse(User user){
        UserResponseModel response = new UserResponseModel();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setBirthDate(user.getBirthDate());
        response.setMobile(user.getMobile());
        response.setAuthorities(user.getAuthorities().stream().map(authority -> authority.getName().name()).collect(Collectors.toSet()));
        response.setAddressUserModel(addressConverter.addressToUserModel(user.getAddress()));
        return response;
    }
    public List<UserResponseModel> usersToResponses(List<User> users){
        List<UserResponseModel> responses = new ArrayList<>();
        for (User user:users){
            responses.add(userToResponse(user));
        }
        return responses;
    }
    public User requestToUser(UserRequestModel request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setBirthDate(request.getBirthDate());
        user.setMobile(request.getMobile());
        user.setAddress(addressConverter.requestToAddress(request.getAddressRequest()));
        return user;
    }
    public UserAdminModel userToAdminModel(User user){
        UserAdminModel userAdminModel = new UserAdminModel();
        userAdminModel.setId(user.getId());
        userAdminModel.setFirstName(user.getFirstName());
        userAdminModel.setLastName(user.getLastName());
        userAdminModel.setEmail(user.getEmail());
        userAdminModel.setPassword(user.getPassword());
        userAdminModel.setBirthDate(user.getBirthDate());
        userAdminModel.setMobile(user.getMobile());
        userAdminModel.setAddressAdminModel(addressConverter.addressToAdminModel(user.getAddress()));
        userAdminModel.setDateCreated(user.getDateCreated());
        userAdminModel.setLastUpdated(user.getLastUpdated());
        Set<String> authorityNames = user.getAuthorities().stream().map(authority -> authority.getName().name()).collect(Collectors.toSet());
        userAdminModel.setAuthorities(authorityNames);
        userAdminModel.setActive(user.isActive());
        return userAdminModel;
    }
    public List<UserAdminModel> usersToAdminModels(List<User> users){
        List<UserAdminModel> adminModels = new ArrayList<>();
        for(User user:users){
            adminModels.add(userToAdminModel(user));
        }
        return adminModels;
    }
}
