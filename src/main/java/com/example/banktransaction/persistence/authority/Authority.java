package com.example.banktransaction.persistence.authority;

import javax.persistence.*;

@Entity
@Table
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private AuthorityType name;

    public Long getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public AuthorityType getName() {
        return name;
    }

    public void setName(AuthorityType name) {
        this.name = name;
    }

}
