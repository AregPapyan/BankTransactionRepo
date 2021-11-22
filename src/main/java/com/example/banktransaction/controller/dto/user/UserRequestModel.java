package com.example.banktransaction.controller.dto.user;

import com.example.banktransaction.controller.dto.address.AddressUserModel;
import java.util.Date;

public class UserRequestModel {
    private String firstName;

    private String lastName;

    private String email;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public AddressUserModel getAddressRequest() {
        return addressUserModel;
    }

    public void setAddressRequest(AddressUserModel addressUserModel) {
        this.addressUserModel = addressUserModel;
    }

    @Override
    public String toString() {
        return "UserRequestModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", mobile='" + mobile + '\'' +
                ", addressUserModel=" + addressUserModel +
                '}';
    }
}
