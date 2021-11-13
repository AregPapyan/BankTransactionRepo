package com.example.banktransaction.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select acc from Account acc where acc.status = 0")
    List<Account> getRequests();
}
