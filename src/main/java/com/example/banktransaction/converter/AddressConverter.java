package com.example.banktransaction.converter;

import com.example.banktransaction.controller.dto.address.AddressAdminModel;
import com.example.banktransaction.controller.dto.address.AddressUserModel;
import com.example.banktransaction.persistence.user.Address;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class AddressConverter {
    public AddressUserModel addressToUserModel(Address address){
        AddressUserModel addressUserModel = new AddressUserModel();
        addressUserModel.setCountry(address.getCountry());
        addressUserModel.setCity(address.getCity());
        addressUserModel.setStreet(address.getStreet());
        addressUserModel.setHouseNumber(address.getHouseNumber());
        addressUserModel.setPostalCode(address.getPostalCode());
        return addressUserModel;
    }
    public Address requestToAddress(AddressUserModel request){
        Address address = new Address();
        address.setCountry(request.getCountry());
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setHouseNumber(request.getHouseNumber());
        address.setPostalCode(request.getPostalCode());
        return address;
    }
    public AddressAdminModel addressToAdminModel(Address address) {
        AddressAdminModel addressAdminModel = new AddressAdminModel();
        addressAdminModel.setId(address.getId());
        addressAdminModel.setCountry(address.getCountry());
        addressAdminModel.setCity(address.getCity());
        addressAdminModel.setStreet(address.getStreet());
        addressAdminModel.setHouseNumber(address.getHouseNumber());
        addressAdminModel.setPostalCode(address.getPostalCode());
        addressAdminModel.setDateCreated(address.getDateCreated());
        addressAdminModel.setLastUpdated(address.getLastUpdated());
        return addressAdminModel;
    }
}
