package by.miner.mono.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayoutDto {
    private Long id;
    private long userId;
    private BigDecimal amount;
    private LocalDateTime issueDate;
    private LocalDateTime approvalDate;

    public PayoutDto() {
    }

    public PayoutDto(Long id, long userId, BigDecimal amount,
                     LocalDateTime issueDate, LocalDateTime approvalDate) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.issueDate = issueDate;
        this.approvalDate = approvalDate;
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

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }
}
