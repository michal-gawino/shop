package com.michal.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "order_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String country;

    private String city;

    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "credit_card_number")
    private String creditCardNumber;

    public OrderDetails() {
    }

    public OrderDetails(String country, String city, String street, String postalCode, String creditCardNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.creditCardNumber = creditCardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
