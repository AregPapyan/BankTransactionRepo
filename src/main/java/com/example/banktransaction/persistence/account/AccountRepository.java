package com.example.banktransaction.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Long,Account> {
}
