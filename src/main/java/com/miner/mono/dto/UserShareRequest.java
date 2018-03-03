package com.miner.mono.dto;

import java.math.BigDecimal;

public final class UserShareRequest {
    private BigDecimal miningInterval;
    private BigDecimal share;

    public UserShareRequest() {
    }

    public UserShareRequest(BigDecimal miningInterval, BigDecimal share) {
        this.miningInterval = miningInterval;
        this.share = share;
    }

    public BigDecimal getMiningInterval() {
        return miningInterval;
    }

    public void setMiningInterval(BigDecimal miningInterval) {
        this.miningInterval = miningInterval;
    }

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }
}
