package by.miner.mono.service;

import by.miner.mono.dto.ApplicationUserDto;
import by.miner.mono.dto.UserProfitItem;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        FlywayTestExecutionListener.class})
@FlywayTest
public class UserProfitServiceTest {
    @Autowired
    private WalletService walletService;
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private UserProfitService userProfitService;

    private ApplicationUserDto user1, user2;

    @Before
    public void setUp() {
        walletService.updateBalance(BigDecimal.TEN);
        user1 = applicationUserService.saveUser(new Credentials("user1", "password"));
        user2 = applicationUserService.saveUser(new Credentials("user2", "password"));
    }

    @Test
    public void saveUserProfit() {
        UserProfitRequest userProfit = new UserProfitRequest(BigDecimal.ONE, BigDecimal.TEN);
        userProfitService.saveUserProfit(user1, userProfit);
        List<UserProfitItem> userProfitItems = userProfitService.calculateProfit();
        assertThat(userProfitItems, hasSize(1));
        assertThat(userProfitItems.get(0).getId(), equalTo(user1.getId()));
        assertThat(userProfitItems.get(0).getUsername(), equalTo(user1.getUsername()));
        assertThat(userProfitItems.get(0).getProfit(), comparesEqualTo(userProfit.getProfit()));
    }

    @Test
    public void distributeProfitByUsers() {
        UserProfitRequest userProfit1 = new UserProfitRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1)), BigDecimal.TEN);
        UserProfitRequest userProfit2 = new UserProfitRequest(BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(9)), BigDecimal.TEN);
        userProfitService.saveUserProfit(user1, userProfit1);
        userProfitService.saveUserProfit(user2, userProfit2);
        List<UserProfitItem> userProfitItems = userProfitService.calculateProfit();
        assertThat(userProfitItems, hasSize(2));
        assertThat(userProfitItems.get(0).getId(), equalTo(user1.getId()));
        assertThat(userProfitItems.get(0).getUsername(), equalTo(user1.getUsername()));
        assertThat(userProfitItems.get(0).getProfit(), comparesEqualTo(BigDecimal.valueOf(1L)));
        assertThat(userProfitItems.get(1).getId(), equalTo(user2.getId()));
        assertThat(userProfitItems.get(1).getUsername(), equalTo(user2.getUsername()));
        assertThat(userProfitItems.get(1).getProfit(), comparesEqualTo(BigDecimal.valueOf(9L)));
    }
}