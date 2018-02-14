package by.miner.mono.dto;

import java.math.BigDecimal;

public class UserProfitItem {
    private Long id;
    private String username;
    private BigDecimal profit;
    private Boolean active;

    public UserProfitItem() {
    }

    public UserProfitItem(Long id, String username, BigDecimal profit, boolean active) {
        this.id = id;
        this.username = username;
        this.profit = profit;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
