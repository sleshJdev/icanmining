package com.miner.mono.service;

import com.miner.mono.dto.WalletDto;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
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
import static org.junit.Assert.assertNotNull;
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
public class WalletServiceTest {
    @Autowired
    private WalletService walletService;

    @Test
    public void findWallet() {
        WalletDto wallet = walletService.findWallet();
        assertNotNull(wallet);
        assertThat(wallet.getBalance(), comparesEqualTo(BigDecimal.ZERO));
        assertThat(wallet.getWithdrawnBtc(), comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void findWalletAddress() {
        String walletAddress = walletService.findWalletAddress();
        assertThat(walletAddress, not(isEmptyOrNullString()));
    }

    @Test
    public void updateBalance() {
        WalletDto wallet = walletService.findWallet();
        assertThat(wallet.getBalance(), comparesEqualTo(BigDecimal.ZERO));
        walletService.updateBalance(BigDecimal.TEN);
        WalletDto updatedWallet = walletService.findWallet();
        assertThat(updatedWallet.getBalance(), comparesEqualTo(BigDecimal.TEN));
    }

    @Test
    public void withdrawalBitcoin() {
        WalletDto wallet0 = walletService.findWallet();
        assertThat(wallet0.getBalance(), comparesEqualTo(BigDecimal.ZERO));

        WalletDto wallet10 = walletService.updateBalance(BigDecimal.TEN);
        assertThat(wallet10.getBalance(), comparesEqualTo(BigDecimal.TEN));

        WalletDto walletDto = walletService.withdrawal(BigDecimal.ONE);
        assertThat(walletDto.getBalance(), comparesEqualTo(BigDecimal.valueOf(9L)));
        assertThat(walletDto.getWithdrawnBtc(), comparesEqualTo(BigDecimal.ONE));
    }
}