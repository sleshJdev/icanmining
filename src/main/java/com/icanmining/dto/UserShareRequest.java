package com.icanmining.dto;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public final class UserShareRequest {
    private BigDecimal miningInterval;
    private BigDecimal profit;

    public UserShareRequest() {
    }

    public UserShareRequest(BigDecimal miningInterval, BigDecimal profit) {
        this.miningInterval = miningInterval;
        this.profit = profit;
    }

    public BigDecimal getMiningInterval() {
        return miningInterval;
    }

    public void setMiningInterval(BigDecimal miningInterval) {
        this.miningInterval = miningInterval;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
