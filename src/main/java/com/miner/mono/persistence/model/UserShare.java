package com.miner.mono.persistence.model;

import com.miner.mono.dto.UserProfitItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.miner.mono.persistence.model.UserShare.USER_PROFIT_MAPPING_NAME;

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
public class UserShare {
    public static final String USER_PROFIT_MAPPING_NAME = "UserProfitItem";
    @Id
    private Long id;
    @MapsId
    @OneToOne
    private ApplicationUser user;
    @Column(nullable = false, precision = 19, scale = 15)
    private BigDecimal share;
    @Column(nullable = false)
    private LocalDateTime lastContributionDate;

    public UserShare() {
    }

    public UserShare(ApplicationUser user, BigDecimal share, LocalDateTime lastContributionDate) {
        this.user = user;
        this.share = share;
        this.lastContributionDate = lastContributionDate;
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

    public BigDecimal getShare() {
        return share;
    }

    public void setShare(BigDecimal share) {
        this.share = share;
    }

    public LocalDateTime getLastContributionDate() {
        return lastContributionDate;
    }

    public void setLastContributionDate(LocalDateTime lastContributionDate) {
        this.lastContributionDate = lastContributionDate;
    }
}
