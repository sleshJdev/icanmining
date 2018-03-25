package com.icanmining.persistence.model;

import com.icanmining.enums.Coin;
import com.icanmining.enums.Currency;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, precision = 19, scale = 15)
    private BigDecimal rate;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Coin coin;

    public ExchangeRate() {
    }

    public ExchangeRate(BigDecimal rate, Currency currency, Coin coin) {
        this.rate = rate;
        this.currency = currency;
        this.coin = coin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }
}
