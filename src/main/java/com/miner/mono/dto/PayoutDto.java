package com.miner.mono.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayoutDto {
    private Long id;
    private long userId;
    private BigDecimal amount;
    private LocalDateTime issueDate;
    private LocalDateTime closeDate;
    private boolean canceled;

    public PayoutDto() {
    }

    public PayoutDto(Long id, long userId, BigDecimal amount,
                     LocalDateTime issueDate, LocalDateTime closeDate,
                     boolean canceled) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.closeDate = closeDate;
        this.canceled = canceled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUser() {
        return userId;
    }

    public void setUser(long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
