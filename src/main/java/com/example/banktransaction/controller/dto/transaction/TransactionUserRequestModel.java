package com.example.banktransaction.controller.dto.transaction;

import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.persistence.transaction.TransactionType;

public class TransactionUserRequestModel {
    private TransactionType type;
    private Double amount;
    private AccountUserResponseModel from;
    private AccountUserResponseModel to;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public AccountUserResponseModel getFrom() {
        return from;
    }

    public void setFrom(AccountUserResponseModel from) {
        this.from = from;
    }

    public AccountUserResponseModel getTo() {
        return to;
    }

    public void setTo(AccountUserResponseModel to) {
        this.to = to;
    }
}
