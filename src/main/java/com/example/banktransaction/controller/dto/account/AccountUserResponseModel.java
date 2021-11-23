package com.example.banktransaction.controller.dto.account;

import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.account.Currency;

import java.util.Date;

public class AccountUserResponseModel {

    private String number;
    private Currency currency;
    //The following 3 field are not needed(?)
    private Date dateCreated;
    private Date lastUpdated;
    private Status status;

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
}
