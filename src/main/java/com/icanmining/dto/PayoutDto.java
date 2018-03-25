package com.icanmining.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayoutDto {
    private Long id;
    private long userId;
    private BigDecimal amount;
    private BigDecimal fee;
    private LocalDateTime issueDate;
    private LocalDateTime closeDate;
    private boolean canceled;

    public PayoutDto() {
    }

    public PayoutDto(Long id, long userId,
                     BigDecimal amount, BigDecimal fee,
                     LocalDateTime issueDate, LocalDateTime closeDate,
                     boolean canceled) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.fee = fee;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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
