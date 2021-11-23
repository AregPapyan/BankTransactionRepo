package com.example.banktransaction.controller.dto.account;

import com.example.banktransaction.persistence.account.Currency;

public class AccountUserRequestModel {
    private String number;
    private Currency currency;


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
