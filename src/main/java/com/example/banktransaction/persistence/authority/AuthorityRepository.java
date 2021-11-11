package com.example.banktransaction.persistence.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority getByName(AuthorityType name);
}
