package com.icanmining.dto;

import org.springframework.data.convert.Jsr310Converters.DateToLocalDateTimeConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class UserProfitItem {
    private final long id;
    private final String username;
    private final BigDecimal profit;
    private final LocalDateTime lastContributionDate;

    public UserProfitItem(long id,
                          String username,
                          BigDecimal profit,
                          Date lastContributionDate) {
        this.id = id;
        this.username = username;
        this.profit = profit;
        this.lastContributionDate = DateToLocalDateTimeConverter.INSTANCE.convert(lastContributionDate);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public LocalDateTime getLastContributionDate() {
        return lastContributionDate;
    }
}
