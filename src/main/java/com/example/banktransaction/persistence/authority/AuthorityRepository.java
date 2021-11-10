package com.example.banktransaction.persistence.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Long,Authority> {
    Authority getByName(AuthorityType name);
}
