package com.example.banktransaction.persistence.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Long,Transaction> {
}
