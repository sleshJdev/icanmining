package by.miner.mono.persistence.model;

import by.miner.mono.dto.UserProfitItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static by.miner.mono.persistence.model.UserProfit.USER_PROFIT_MAPPING_NAME;

@SqlResultSetMapping(
        name = USER_PROFIT_MAPPING_NAME,
        classes = @ConstructorResult(
                targetClass = UserProfitItem.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "profit", type = BigDecimal.class),
                        @ColumnResult(name = "active", type = Boolean.class)
                }
        )
)
@Entity
public class UserProfit {
    public static final String USER_PROFIT_MAPPING_NAME = "by.miner.mono.dto.UserProfitItem";
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private ApplicationUser user;
    @Column(updatable = false, nullable = false, precision = 19, scale = 15)
    private BigDecimal profit;
    @Column(updatable = false, nullable = false)
    private LocalDateTime issueDate;
    @Column(updatable = false, nullable = false, precision = 19, scale = 0)
    private BigDecimal miningInterval;

    public UserProfit() {
    }

    public UserProfit(ApplicationUser user, BigDecimal profit, BigDecimal miningInterval, LocalDateTime issueDate) {
        this.user = user;
        this.profit = profit;
        this.miningInterval = miningInterval;
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

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getMiningInterval() {
        return miningInterval;
    }

    public void setMiningInterval(BigDecimal miningInterval) {
        this.miningInterval = miningInterval;
    }
}
