package com.icanmining.service;

import com.icanmining.dto.ApplicationUserDto;
import com.icanmining.dto.PayoutDto;
import com.icanmining.dto.UserProfitItem;
import com.icanmining.dto.UserShareRequest;
import com.icanmining.exception.InsufficientFundsException;
import com.icanmining.security.Credentials;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PayoutServiceTest extends AbstractServiceTest {
    @Autowired
    private PayoutService payoutService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserShareService userShareService;
    @Value("${app.core.min_payout}")
    private BigDecimal minPayout;
    @Value("${app.core.fee_percents}")
    private BigDecimal feePercents;
    private BigDecimal fee;
    private ApplicationUserDto user1, user2, user3;

    @Before
    public void setUp() throws Exception {
        walletService.updateBalance(WALLET_BALANCE);
        user1 = applicationUserService.saveUser(new Credentials("user1", "password"));
        user2 = applicationUserService.saveUser(new Credentials("user2", "password"));
        user3 = applicationUserService.saveUser(new Credentials("user3", "password"));
        fee = feePercents.movePointLeft(2);
    }

    @Test
    public void createPayout() {
        UserShareRequest userShare = new UserShareRequest(DAY_MINING_INTERVAL, WALLET_BALANCE);
        userShareService.contribute(user1, userShare);
        UserProfitItem userProfit = userShareService.calculateProfit(user1.getId());
        BigDecimal profit = userProfit.getProfit();
        assertThat(profit, comparesEqualTo(WALLET_BALANCE));

        BigDecimal amountBtc = BigDecimal.ONE;
        BigDecimal feeAmount = amountBtc.multiply(fee);
        BigDecimal actualAmount = amountBtc.subtract(feeAmount);
        PayoutDto payout = payoutService.createPayout(user1.getId(), amountBtc);
        assertThat(payout.getAmount(), comparesEqualTo(actualAmount));
        assertThat(payout.getFee(), comparesEqualTo(feeAmount));

        UserProfitItem userProfitAfterPayout = userShareService.calculateProfit(user1.getId());
        assertThat(userProfitAfterPayout.getProfit(), comparesEqualTo(profit.subtract(amountBtc)));
    }

    @Test
    public void approvePayout() {
        UserShareRequest shareRequest = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.TEN);
        userShareService.contribute(user1, shareRequest);

        PayoutDto payout = payoutService.createPayout(user1.getId(), BigDecimal.ONE);
        assertThat(payout.getIssueDate(), notNullValue());
        assertThat(payout.getCloseDate(), nullValue());
        assertFalse(payout.isCanceled());

        PayoutDto approvedPayout = payoutService.approvePayout(payout.getId());
        assertThat(approvedPayout.getCloseDate(), notNullValue());
        assertFalse(approvedPayout.isCanceled());
    }

    @Test
    public void payoutWithSeveralUsers() {
        UserShareRequest userShare1 = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.valueOf(0.4D));
        UserShareRequest userShare2 = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.valueOf(0.3D));
        UserShareRequest userShare3 = new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.valueOf(0.3D));
        userShareService.contribute(user1, userShare1);
        userShareService.contribute(user2, userShare2);
        userShareService.contribute(user3, userShare3);
        Map<Long, UserProfitItem> userProfitItemsBeforePayout = userShareService.calculateProfits()
                .stream().collect(toMap(UserProfitItem::getId, Function.identity()));
        assertThat(userProfitItemsBeforePayout.entrySet(), hasSize(3));
        BigDecimal profit1 = WALLET_BALANCE.multiply(userShare1.getProfit());
        BigDecimal profit2 = WALLET_BALANCE.multiply(userShare2.getProfit());
        BigDecimal profit3 = WALLET_BALANCE.multiply(userShare3.getProfit());
        assertThat(userProfitItemsBeforePayout.get(user1.getId()).getProfit(), comparesEqualTo(profit1));
        assertThat(userProfitItemsBeforePayout.get(user2.getId()).getProfit(), comparesEqualTo(profit2));
        assertThat(userProfitItemsBeforePayout.get(user3.getId()).getProfit(), comparesEqualTo(profit3));

        PayoutDto payout1 = payoutService.createPayout(user1.getId(), BigDecimal.ONE);
        PayoutDto payout3 = payoutService.createPayout(user3.getId(), BigDecimal.ONE);
        payoutService.approvePayout(payout1.getId());
        payoutService.approvePayout(payout3.getId());

        Map<Long, UserProfitItem> userProfitItemsAfterPayout = userShareService.calculateProfits()
                .stream().collect(toMap(UserProfitItem::getId, Function.identity()));
        assertThat(userProfitItemsAfterPayout.entrySet(), hasSize(3));
        assertThat(userProfitItemsAfterPayout.get(user1.getId()).getProfit(),
                comparesEqualTo(profit1.subtract(payout1.getAmount().add(payout1.getFee()))));
        assertThat(userProfitItemsAfterPayout.get(user2.getId()).getProfit(), comparesEqualTo(profit2));
        assertThat(userProfitItemsAfterPayout.get(user3.getId()).getProfit(),
                comparesEqualTo(profit3.subtract(payout3.getAmount().add(payout3.getFee()))));
    }

    @Test
    public void cancelPayout() {
        userShareService.contribute(user1, new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.ONE));
        PayoutDto payout = payoutService.createPayout(user1.getId(), BigDecimal.ONE);
        assertFalse(payout.isCanceled());
        PayoutDto canceledPayout = payoutService.cancelPayout(payout.getId());
        assertTrue(canceledPayout.isCanceled());
    }

    @Test
    public void findApprovalPendingPayouts() {
        BigDecimal profit1 = BigDecimal.valueOf(4L);
        BigDecimal profit2 = BigDecimal.valueOf(3L);
        BigDecimal profit3 = BigDecimal.valueOf(3L);
        UserShareRequest userShare1 = new UserShareRequest(DAY_MINING_INTERVAL, profit1);
        UserShareRequest userShare2 = new UserShareRequest(DAY_MINING_INTERVAL, profit2);
        UserShareRequest userShare3 = new UserShareRequest(DAY_MINING_INTERVAL, profit3);
        userShareService.contribute(user1, userShare1);
        userShareService.contribute(user2, userShare2);
        userShareService.contribute(user3, userShare3);
        PayoutDto payout1 = payoutService.createPayout(user1.getId(), BigDecimal.ONE);
        PayoutDto payout2 = payoutService.createPayout(user2.getId(), BigDecimal.ONE);
        PayoutDto payout3 = payoutService.createPayout(user3.getId(), BigDecimal.ONE);
        List<PayoutDto> payouts = payoutService.findApprovalPendingPayouts();
        assertThat(payouts, hasSize(3));
        payoutService.cancelPayout(payout1.getId());
        payoutService.approvePayout(payout2.getId());
        List<PayoutDto> approvalPendingPayouts = payoutService.findApprovalPendingPayouts();
        assertThat(approvalPendingPayouts, hasSize(1));
        PayoutDto approvalPendingPayout = approvalPendingPayouts.get(0);
        assertThat(approvalPendingPayout.getId(), equalTo(payout3.getId()));
        assertThat(approvalPendingPayout.getCloseDate(), nullValue());
        assertThat(approvalPendingPayout.getIssueDate(), notNullValue());
        assertFalse(approvalPendingPayout.isCanceled());
        assertThat(approvalPendingPayout.getAmount(), comparesEqualTo(payout3.getAmount()));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void payoutMoreWalletBalance() {
        userShareService.contribute(user1, new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.ONE));
        UserProfitItem userProfitItem = userShareService.calculateProfit(user1.getId());
        BigDecimal amountBtc = userProfitItem.getProfit().add(BigDecimal.ONE);
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage(String.format(
                "Insufficient funds. It is not enough funds for withdrawal %f BTC.",
                amountBtc.doubleValue()));
        payoutService.createPayout(user1.getId(), amountBtc);
    }

    @Test
    public void payoutLessThanMinimal() {
        userShareService.contribute(user1, new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.ONE));
        BigDecimal amountBtc = minPayout.divide(BigDecimal.TEN, 15, RoundingMode.HALF_UP);
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage(String.format(
                "Insufficient funds. You need have minimal %f BTC to withdrawal.",
                minPayout.doubleValue()));
        payoutService.createPayout(user1.getId(), amountBtc);
    }

    @Test
    public void payoutZeroBtc() {
        userShareService.contribute(user1, new UserShareRequest(DAY_MINING_INTERVAL, BigDecimal.ONE));
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("Payout amount should be great than zero.");
        payoutService.createPayout(user1.getId(), BigDecimal.ZERO);
    }
}