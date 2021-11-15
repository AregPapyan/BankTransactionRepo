package com.example.banktransaction.controller.dto.transaction;

import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.persistence.transaction.TransactionType;

public class TransactionUserRequestModel {
    private TransactionType type;
    private Double amount;
    private String from;
    private String to;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
