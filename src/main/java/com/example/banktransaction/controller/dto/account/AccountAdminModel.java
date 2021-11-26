package com.example.banktransaction.controller.dto.account;

import com.example.banktransaction.controller.dto.user.UserAdminModel;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.Currency;

import java.util.Date;

public class AccountAdminModel {
    private Long id;
    private String number;
    private Currency currency;
    private Date dateCreated;
    private Date lastUpdated;
    private Status status;
    private UserAdminModel userAdminModel;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserAdminModel getUserAdminModel() {
        return userAdminModel;
    }

    public void setUserAdminModel(UserAdminModel userAdminModel) {
        this.userAdminModel = userAdminModel;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
