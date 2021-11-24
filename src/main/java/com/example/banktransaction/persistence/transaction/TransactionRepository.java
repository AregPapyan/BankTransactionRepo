package com.example.banktransaction.persistence.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE t.from.user.id=?1 OR t.to.user.id=?1")
    List<Transaction> getAllByUserId(Long id);
    @Query("SELECT t FROM Transaction t order by t.from.user.id")
    List<Transaction> getAllOrdered();
}
