package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.dto.UserShareRequest;
import com.miner.mono.security.Credentials;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserShareServiceTest extends AbstractServiceTest {
    @Autowired
    private WalletService walletService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private UserShareService userShareService;

    private ApplicationUserDto user1, user2;

    @Before
    public void setUp() {
        walletService.updateBalance(BigDecimal.TEN);
        user1 = applicationUserService.saveUser(new Credentials("user1", "password"));
        user2 = applicationUserService.saveUser(new Credentials("user2", "password"));

        UserShareRequest userProfit1 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.ONE);
        UserShareRequest userProfit2 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(9)), BigDecimal.ONE);
        userShareService.contribute(user1, userProfit1);
        userShareService.contribute(user2, userProfit2);
    }

    @Test
    public void calculateProfitById() {
        UserProfitItem userProfitItem1 = userShareService.calculateProfit(user1.getId());
        UserProfitItem userProfitItem2 = userShareService.calculateProfit(user2.getId());
        assertUserProfit(userProfitItem1, user1, BigDecimal.valueOf(1L));
        assertUserProfit(userProfitItem2, user2, BigDecimal.valueOf(9L));
    }

    @Test
    public void distributeProfitByUsers() {
        List<UserProfitItem> userProfitItems = userShareService.calculateProfits();
        assertThat(userProfitItems, hasSize(2));
        assertUserProfit(userProfitItems.get(0), user1, BigDecimal.valueOf(1L));
        assertUserProfit(userProfitItems.get(1), user2, BigDecimal.valueOf(9L));
    }

    @Test
    public void distributeWithWalletWithdrawal() {
        List<UserProfitItem> userProfitItems = userShareService.calculateProfits();
        assertThat(userProfitItems, hasSize(2));
        assertUserProfit(userProfitItems.get(0), user1, BigDecimal.valueOf(1L));
        assertUserProfit(userProfitItems.get(1), user2, BigDecimal.valueOf(9L));

        walletService.withdrawal(BigDecimal.valueOf(5L));

        List<UserProfitItem> usersProfitAfterWithdrawal = userShareService.calculateProfits();
        assertThat(usersProfitAfterWithdrawal, hasSize(2));
        assertUserProfit(usersProfitAfterWithdrawal.get(0), user1, BigDecimal.valueOf(1L));
        assertUserProfit(usersProfitAfterWithdrawal.get(1), user2, BigDecimal.valueOf(9L));
    }

    private void assertUserProfit(UserProfitItem userProfit, ApplicationUserDto user, BigDecimal profit) {
        assertThat(userProfit.getId(), equalTo(user.getId()));
        assertThat(userProfit.getUsername(), equalTo(user.getUsername()));
        assertThat(userProfit.getProfit(), comparesEqualTo(profit));
    }
}