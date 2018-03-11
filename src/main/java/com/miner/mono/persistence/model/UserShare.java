package com.miner.mono.persistence.model;

import com.miner.mono.dto.UserProfitItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static com.miner.mono.persistence.repository.UserShareRepository.*;


@NamedNativeQueries({
        @NamedNativeQuery(
                name = CALCULATE_USERS_PROFIT_QUERY_NAME,
                query = CALCULATE_USERS_PROFIT_QUERY,
                resultSetMapping = USER_SHARE_MAPPING_NAME
        ),
        @NamedNativeQuery(
                name = CALCULATE_USER_PROFIT_QUERY_NAME,
                query = CALCULATE_USER_PROFIT_QUERY,
                resultSetMapping = USER_SHARE_MAPPING_NAME
        )
})
@SqlResultSetMapping(
        name = USER_SHARE_MAPPING_NAME,
        classes = @ConstructorResult(
                targetClass = UserProfitItem.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "username", type = String.class),
                        @ColumnResult(name = "profit", type = BigDecimal.class),
                        @ColumnResult(name = "last_contribution_date", type = Date.class)
                }
        )
)
@Entity
public class UserShare {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
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
