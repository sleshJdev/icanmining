package by.miner.mono.service;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.dto.UserProfitDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserProfitServiceTest {

    @Autowired
    private UserProfitService userProfitService;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    private ApplicationUser user;

    @Before
    public void setUp() throws Exception {
        user = applicationUserRepository.save(new ApplicationUser("test"+System.currentTimeMillis(), "test"));
    }

    @Test
    public void saveUserProfit() {
        UserProfitDto dto = new UserProfitDto();
        dto.setMiningInterval(BigDecimal.valueOf(1000));
        dto.setProfit(BigDecimal.TEN);
        userProfitService.saveUserProfit(user, dto);
    }
}