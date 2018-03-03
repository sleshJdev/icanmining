package com.miner.mono.dto;

import java.math.BigDecimal;

public class UserProfitItem {
    private long id;
    private String username;
    private BigDecimal profit;
    private boolean active;

    public UserProfitItem() {
    }

    public UserProfitItem(long id, String username, BigDecimal profit, boolean active) {
        this.id = id;
        this.username = username;
        this.profit = profit;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
