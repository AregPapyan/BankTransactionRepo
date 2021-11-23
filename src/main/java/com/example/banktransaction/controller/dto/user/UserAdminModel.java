package com.example.banktransaction.controller.dto.user;

import com.example.banktransaction.controller.dto.address.AddressAdminModel;
import com.sun.istack.NotNull;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

public class UserAdminModel {
    private Long id;


    private String firstName;


    private String lastName;


    private String email;


    private String password;

    private Date birthDate;
    private String mobile;
    private AddressAdminModel addressAdminModel;
    private Date dateCreated;
    private Date lastUpdated;
    private Set<String> authorities;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public AddressAdminModel getAddressAdminModel() {
        return addressAdminModel;
    }

    public void setAddressAdminModel(AddressAdminModel addressAdminModel) {
        this.addressAdminModel = addressAdminModel;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
