package by.miner.mono.service;

import by.miner.mono.dto.ApplicationUserDto;
import by.miner.mono.dto.PayoutDto;
import by.miner.mono.dto.UserProfitRequest;
import by.miner.mono.security.Credentials;
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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class})
@FlywayTest
public class PayoutServiceTest {
    @Autowired
    private PayoutService payoutService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserProfitService userProfitService;
    private ApplicationUserDto user1;

    @Before
    public void setUp() throws Exception {
        walletService.updateBalance(BigDecimal.TEN);
        user1 = applicationUserService.saveUser(new Credentials("user1", "password"));
    }

    @Test
    public void createPayout() {
        UserProfitRequest userProfit = new UserProfitRequest(BigDecimal.ONE, BigDecimal.TEN);
        userProfitService.saveUserProfit(user1, userProfit);
        PayoutDto payout = payoutService.createPayout(user1);
        assertThat(payout.getIssueDate(), notNullValue());
        assertThat(payout.getApprovalDate(), nullValue());
        assertThat(payout.getAmount(), comparesEqualTo(userProfit.getProfit()));
    }

    @Test
    public void approvePayout() {
        UserProfitRequest userProfit = new UserProfitRequest(BigDecimal.ONE, BigDecimal.TEN);
        userProfitService.saveUserProfit(user1, userProfit);
        PayoutDto payout = payoutService.createPayout(user1);
        assertThat(payout.getIssueDate(), notNullValue());
        assertThat(payout.getApprovalDate(), nullValue());
        assertThat(payout.getAmount(), comparesEqualTo(userProfit.getProfit()));
        payoutService.approvePayout(payout.getId());
        PayoutDto approvedPayout = payoutService.getPayout(payout.getId());
        assertThat(approvedPayout.getApprovalDate(), notNullValue());
    }
}