package com.example.banktransaction.persistence.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.from.user.id=?1 OR t.to.user.id=?1")
    List<Transaction> getAllByUserId(Long id);
    //Balance
    @Query("select  t From Transaction t where (t.from.number = ?1 and t.type = 0) or (t.to.number = ?1 and t.type = 2)")
    List<Transaction> getAddingByAccount(String number);
}
