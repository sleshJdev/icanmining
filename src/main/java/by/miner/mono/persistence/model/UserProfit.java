package by.miner.mono.persistence.model;

import by.miner.mono.enums.AlgorithmType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.*;

@Entity
public class UserProfit {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private ApplicationUser user;
    @Column(nullable = false)
    private BigDecimal profit;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlgorithmType algorithmType;
    @Column(updatable = false, nullable = false)
    private LocalDateTime issueDate;
    @Column(updatable = false, nullable = false)
    private BigDecimal miningPeriod;

    public UserProfit() {
    }

    public UserProfit(ApplicationUser user, BigDecimal profit, AlgorithmType algorithmType, BigDecimal miningPeriod, LocalDateTime issueDate) {
        this.user = user;
        this.profit = profit;
        this.algorithmType = algorithmType;
        this.miningPeriod = miningPeriod;
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

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getMiningPeriod() {
        return miningPeriod;
    }

    public void setMiningPeriod(BigDecimal miningPeriod) {
        this.miningPeriod = miningPeriod;
    }
}
