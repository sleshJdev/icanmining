package by.miner.mono.dto;

import java.math.BigDecimal;

public final class UserProfitRequest {
    private BigDecimal miningInterval;
    private BigDecimal profit;

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
