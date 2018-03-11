package com.miner.mono.service;

import com.miner.mono.config.Profiles;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@TestExecutionListeners(
        listeners = {
                DependencyInjectionTestExecutionListener.class,
                FlywayTestExecutionListener.class},
        mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@FlywayTest(invokeCleanDB = false)
@ActiveProfiles(Profiles.TEST)
public abstract class AbstractServiceTest {
    protected static final BigDecimal DAY_MINING_INTERVAL = BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1));
    protected static final BigDecimal WALLET_BALANCE = BigDecimal.valueOf(15);
}
