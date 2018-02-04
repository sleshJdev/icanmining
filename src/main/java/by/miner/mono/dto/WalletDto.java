package by.miner.mono.dto;

import java.math.BigDecimal;

public final class WalletDto {
    private final String address;
    private final BigDecimal balance;

    public WalletDto(String address, BigDecimal balance) {
        this.address = address;
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
