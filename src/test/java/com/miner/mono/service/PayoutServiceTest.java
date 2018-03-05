package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.PayoutDto;
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
import static org.junit.Assert.*;

public class PayoutServiceTest extends AbstractServiceTest {
    private static final BigDecimal WALLET_BALANCE = BigDecimal.TEN;
    @Autowired
    private PayoutService payoutService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserShareService userShareService;
    private ApplicationUserDto user1, user2, user3;

    @Before
    public void setUp() throws Exception {
        walletService.updateBalance(WALLET_BALANCE);
        user1 = applicationUserService.saveUser(new Credentials("user1", "password"));
        user2 = applicationUserService.saveUser(new Credentials("user2", "password"));
        user3 = applicationUserService.saveUser(new Credentials("user3", "password"));
    }

    @Test
    public void createPayout() {
        UserShareRequest userProfit = new UserShareRequest(BigDecimal.ONE, BigDecimal.TEN);
        userShareService.contribute(user1, userProfit);
        PayoutDto payout = payoutService.createPayout(user1);
        assertThat(payout.getIssueDate(), notNullValue());
        assertThat(payout.getCloseDate(), nullValue());
        assertThat(payout.getAmount(), comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void approvePayout() {
        UserShareRequest shareRequest = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.TEN);
        userShareService.contribute(user1, shareRequest);
        PayoutDto payout = payoutService.createPayout(user1);

        PayoutDto approvedPayout = payoutService.approvePayout(payout.getId());
        assertThat(approvedPayout.getCloseDate(), notNullValue());
        assertThat(approvedPayout.getAmount(), comparesEqualTo(WALLET_BALANCE));

        UserProfitItem userProfitAfterPayout = userShareService.calculateProfit(user1.getId());
        assertThat(userProfitAfterPayout.getProfit(), comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void payoutWithSeveralUsers() {
        UserShareRequest userShare1 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.valueOf(4L));
        UserShareRequest userShare2 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.valueOf(3L));
        UserShareRequest userShare3 = new UserShareRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.valueOf(3L));
        userShareService.contribute(user1, userShare1);
        userShareService.contribute(user2, userShare2);
        userShareService.contribute(user3, userShare3);
        List<UserProfitItem> userProfitItemsBeforePayout = userShareService.calculateProfits();
        assertThat(userProfitItemsBeforePayout, hasSize(3));
        assertThat(userProfitItemsBeforePayout.get(0).getProfit(), comparesEqualTo(userShare1.getShare()));
        assertThat(userProfitItemsBeforePayout.get(1).getProfit(), comparesEqualTo(userShare2.getShare()));
        assertThat(userProfitItemsBeforePayout.get(2).getProfit(), comparesEqualTo(userShare3.getShare()));

        PayoutDto payout = payoutService.createPayout(user1);
        PayoutDto approvePayout = payoutService.approvePayout(payout.getId());
        assertThat(approvePayout.getAmount(), comparesEqualTo(userShare1.getShare()));

        List<UserProfitItem> userProfitItemsAfterPayout = userShareService.calculateProfits();
        assertThat(userProfitItemsAfterPayout, hasSize(3));
        assertThat(userProfitItemsAfterPayout.get(0).getProfit(), comparesEqualTo(BigDecimal.ZERO));
        assertThat(userProfitItemsAfterPayout.get(1).getProfit(), comparesEqualTo(userShare2.getShare()));
        assertThat(userProfitItemsAfterPayout.get(2).getProfit(), comparesEqualTo(userShare3.getShare()));
    }

    @Test
    public void cancelPayout() {
        PayoutDto payout = payoutService.createPayout(user1);
        assertFalse(payout.isCanceled());
        PayoutDto canceledPayout = payoutService.cancelPayout(payout.getId());
        assertTrue(canceledPayout.isCanceled());
    }

    @Test
    public void findApprovalPendingPayouts() {
        PayoutDto payout1 = payoutService.createPayout(user1);
        PayoutDto payout2 = payoutService.createPayout(user2);
        PayoutDto payout3 = payoutService.createPayout(user3);
        List<PayoutDto> payouts = payoutService.findApprovalPendingPayouts();
        assertThat(payouts, hasSize(3));
        payoutService.cancelPayout(payout1.getId());
        payoutService.approvePayout(payout2.getId());
        List<PayoutDto> onePayouts = payoutService.findApprovalPendingPayouts();
        assertThat(onePayouts, hasSize(1));
        assertThat(onePayouts.get(0).getId(), equalTo(payout3.getId()));
        assertThat(onePayouts.get(0).getCloseDate(), nullValue());
        assertThat(onePayouts.get(0).getIssueDate(), notNullValue());
        assertFalse(onePayouts.get(0).isCanceled());
        assertThat(onePayouts.get(0).getAmount(), comparesEqualTo(BigDecimal.ZERO));
    }
}