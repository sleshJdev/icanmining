package by.miner.mono.web.dto;

import by.miner.mono.enums.AlgorithmType;

import java.math.BigDecimal;

public final class UserProfitDto {
    private BigDecimal miningInterval;
    private AlgorithmType algorithmType;
    private BigDecimal profit;

    public BigDecimal getMiningInterval() {
        return miningInterval;
    }

    public void setMiningInterval(BigDecimal miningInterval) {
        this.miningInterval = miningInterval;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
