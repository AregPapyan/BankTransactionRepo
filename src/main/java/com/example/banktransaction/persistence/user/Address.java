package com.example.banktransaction.persistence.user;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String city;

    private String street;

    private int houseNumber;

    private String postalCode;

    private Date dateCreated;

    private Date lastUpdated;

//    public Address() {
//    }
//
//    public Address(String country, String city, String street, int houseNumber, String postalCode, Date dateCreated, Date lastUpdated) {
//        this.country = country;
//        this.city = city;
//        this.street = street;
//        this.houseNumber = houseNumber;
//        this.postalCode = postalCode;
//        this.dateCreated = dateCreated;
//        this.lastUpdated = lastUpdated;
//    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
