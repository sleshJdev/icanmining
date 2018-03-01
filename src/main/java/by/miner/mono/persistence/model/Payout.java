package by.miner.mono.persistence.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payout {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser user;
    @Column(nullable = false, updatable = false, precision = 19, scale = 15)
    private BigDecimal amount;
    @Column(nullable = false, updatable = false)
    private LocalDateTime issueDate;
    @Column(insertable = false)
    private LocalDateTime approvalDate;


    public Payout() {
    }

    public Payout(ApplicationUser user, BigDecimal amount, LocalDateTime issueDate) {
        this.user = user;
        this.amount = amount;
        this.issueDate = issueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
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
