package com.icanmining.persistence.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class WalletStat {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
    @Column(nullable = false, precision = 19, scale = 15)
    private BigDecimal balance;
    @Column(nullable = false)
    private LocalDateTime date;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String json;

    public WalletStat() {
    }

    public WalletStat(Wallet wallet, BigDecimal balance, LocalDateTime date, String json) {
        this.wallet = wallet;
        this.balance = balance;
        this.date = date;
        this.json = json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
