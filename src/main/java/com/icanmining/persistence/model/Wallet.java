package com.icanmining.persistence.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;
    @NaturalId
    @Column(nullable = false, length = 34)
    private String address;
    @Column(nullable = false, precision = 19, scale = 15)
    private BigDecimal balance;

    public Wallet() {
    }

    public Wallet(String address, BigDecimal balance) {
        this.address = address;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
