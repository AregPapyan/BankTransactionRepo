package com.example.banktransaction.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
//    @Query("select acc from Account acc where acc.status = 0")
//    List<Account> getRequests();

    @Query(value = "select * from account where user_id=?1", nativeQuery = true)
    List<Account> getAllByUserId(Long id);

    Account getAccountByNumber(String number);
}
