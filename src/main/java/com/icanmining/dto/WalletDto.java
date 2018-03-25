package com.icanmining.dto;

import java.math.BigDecimal;

public final class WalletDto {
    private final String address;
    private final BigDecimal balance;
    private final BigDecimal usdAmount;

    public WalletDto(String address, BigDecimal balance, BigDecimal usdAmount) {
        this.address = address;
        this.balance = balance;
        this.usdAmount = usdAmount;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getUsdAmount() {
        return usdAmount;
    }
}
