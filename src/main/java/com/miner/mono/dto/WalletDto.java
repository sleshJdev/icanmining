package com.miner.mono.dto;

import java.math.BigDecimal;

public final class WalletDto {
    private final String address;
    private final BigDecimal balance;
    private final BigDecimal usdAmount;
    private final BigDecimal withdrawnBtc;

    public WalletDto(String address, BigDecimal balance, BigDecimal withdrawnBtc, BigDecimal usdAmount) {
        this.address = address;
        this.balance = balance;
        this.usdAmount = usdAmount;
        this.withdrawnBtc = withdrawnBtc;
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

    public BigDecimal getWithdrawnBtc() {
        return withdrawnBtc;
    }
}
