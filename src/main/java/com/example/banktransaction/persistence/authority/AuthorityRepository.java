package com.example.banktransaction.persistence.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority getByName(AuthorityType name);
}
