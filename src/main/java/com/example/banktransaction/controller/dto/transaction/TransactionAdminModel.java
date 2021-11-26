package com.example.banktransaction.controller.dto.transaction;

import com.example.banktransaction.controller.dto.account.AccountAdminModel;
import com.example.banktransaction.persistence.Status;
import com.example.banktransaction.persistence.transaction.TransactionType;

import java.util.Date;

public class TransactionAdminModel {
    private Long id;
    private TransactionType type;
    private Double amount;
    private AccountAdminModel from;
    private AccountAdminModel to;
    private Date dateCreated;
    private Date lastUpdated;
    private Status status;
    private boolean isActive;

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

    public AccountAdminModel getFrom() {
        return from;
    }

    public void setFrom(AccountAdminModel from) {
        this.from = from;
    }

    public AccountAdminModel getTo() {
        return to;
    }

    public void setTo(AccountAdminModel to) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
