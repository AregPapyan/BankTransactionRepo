package com.example.banktransaction.controller.dto.transaction;

import com.example.banktransaction.controller.dto.account.AccountUserResponseModel;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.transaction.TransactionType;

import java.util.Date;

public class TransactionUserResponseModel {
    private Long id;
    private TransactionType type;
    private Double amount;
    private AccountUserResponseModel from;
    private AccountUserResponseModel to;
    private Date dateCreated;
    private Date lastUpdated;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "TransactionUserResponseModel{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", from=" + from +
                ", to=" + to +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", status=" + status +
                '}';
    }
}
