package com.example.banktransaction.controller.dto.account;

import com.example.banktransaction.persistence.account.Currency;

public class AccountUserRequestModel {
    private Long user_id;
    private String number;
    private Currency currency;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
}
