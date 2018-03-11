package com.miner.mono.service;

import com.miner.mono.dto.WalletDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class WalletServiceTest extends AbstractServiceTest {
    @Autowired
    private WalletService walletService;

    @Test
    public void findWallet() {
        WalletDto wallet = walletService.findWallet();
        assertNotNull(wallet);
        assertThat(wallet.getBalance(), comparesEqualTo(BigDecimal.ZERO));
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
    public void withdrawal() {
        WalletDto wallet0 = walletService.findWallet();
        assertThat(wallet0.getBalance(), comparesEqualTo(BigDecimal.ZERO));

        WalletDto wallet10 = walletService.updateBalance(BigDecimal.TEN);
        assertThat(wallet10.getBalance(), comparesEqualTo(BigDecimal.TEN));
    }
}