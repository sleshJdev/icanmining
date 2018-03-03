package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.dto.UserShareRequest;
import com.miner.mono.security.Credentials;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@TestExecutionListeners(
        listeners = {
                DependencyInjectionTestExecutionListener.class,
                FlywayTestExecutionListener.class},
        mergeMode = MERGE_WITH_DEFAULTS)
@FlywayTest(invokeCleanDB = false)
public class UserShareServiceTest {
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
    }

    @Test
    public void contributeShare() {
        UserShareRequest shareRequest = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.TEN);
        userShareService.contribute(user1, shareRequest);
        UserProfitItem userProfitItem = userShareService.calculateProfit(user1.getId());
        assertThat(userProfitItem.getId(), equalTo(user1.getId()));
        assertThat(userProfitItem.getUsername(), equalTo(user1.getUsername()));
        assertThat(userProfitItem.getProfit(), comparesEqualTo(shareRequest.getShare()));
    }

    @Test
    public void distributeProfitByUsers() {
        UserShareRequest userProfit1 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.ONE);
        UserShareRequest userProfit2 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(9)), BigDecimal.ONE);
        userShareService.contribute(user1, userProfit1);
        userShareService.contribute(user2, userProfit2);
        List<UserProfitItem> userProfitItems = userShareService.calculateProfits();
        assertThat(userProfitItems, hasSize(2));
        assertThat(userProfitItems.get(0).getId(), equalTo(user1.getId()));
        assertThat(userProfitItems.get(0).getUsername(), equalTo(user1.getUsername()));
        assertThat(userProfitItems.get(0).getProfit(), comparesEqualTo(BigDecimal.valueOf(1L)));
        assertThat(userProfitItems.get(1).getId(), equalTo(user2.getId()));
        assertThat(userProfitItems.get(1).getUsername(), equalTo(user2.getUsername()));
        assertThat(userProfitItems.get(1).getProfit(), comparesEqualTo(BigDecimal.valueOf(9L)));
    }
}