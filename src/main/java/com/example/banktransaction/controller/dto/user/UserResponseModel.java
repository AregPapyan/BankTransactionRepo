package com.example.banktransaction.controller.dto.user;

import com.example.banktransaction.controller.dto.address.AddressUserModel;

import java.util.Date;

public class UserResponseModel {
    private String firstName;

    private String lastName;

    private String email;

    private Date birthDate;

    private String mobile;

    private AddressUserModel addressUserModel;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public AddressUserModel getAddressUserModel() {
        return addressUserModel;
    }

    public void setAddressUserModel(AddressUserModel addressUserModel) {
        this.addressUserModel = addressUserModel;
    }
}
