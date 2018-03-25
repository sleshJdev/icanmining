package com.icanmining.service;

import com.icanmining.dto.ApplicationUserDto;
import com.icanmining.dto.UserProfitItem;
import com.icanmining.dto.UserShareRequest;
import com.icanmining.security.Credentials;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserShareServiceTest extends AbstractServiceTest {
    @Autowired
    private WalletService walletService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private UserShareService userShareService;
    @Autowired
    private PayoutService payoutService;

    private ApplicationUserDto user1, user2;

    @Before
    public void setUp() {
        walletService.updateBalance(BigDecimal.TEN);
        user1 = applicationUserService.saveUser(new Credentials("user1", "password"));
        user2 = applicationUserService.saveUser(new Credentials("user2", "password"));

        UserShareRequest userProfit1 = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.ONE);
        UserShareRequest userProfit2 = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.valueOf(4.5D));
        UserShareRequest userProfit3 = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.valueOf(4.5D));
        userShareService.contribute(user1, userProfit1);
        userShareService.contribute(user2, userProfit2);
        userShareService.contribute(user2, userProfit3);
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

    private void assertUserProfit(UserProfitItem userProfit, ApplicationUserDto user, BigDecimal profit) {
        assertThat(userProfit.getId(), equalTo(user.getId()));
        assertThat(userProfit.getUsername(), equalTo(user.getUsername()));
        assertThat(userProfit.getProfit(), comparesEqualTo(profit));
    }
}