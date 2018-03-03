package com.miner.mono.service;

import com.miner.mono.dto.WalletDto;
import com.miner.mono.enums.Currency;
import com.miner.mono.persistence.model.ExchangeRate;
import com.miner.mono.persistence.model.Wallet;
import com.miner.mono.persistence.repository.ExchangeRateRepository;
import com.miner.mono.persistence.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public WalletService(WalletRepository walletRepository, ExchangeRateRepository exchangeRateRepository) {
        this.walletRepository = walletRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Transactional(readOnly = true)
    public WalletDto findWallet() {
        Wallet wallet = walletRepository.findWallet();
        ExchangeRate usdRate = exchangeRateRepository.findByCurrency(Currency.USD);
        BigDecimal usdAmount = wallet.getBalance().multiply(usdRate.getRate());
        return new WalletDto(wallet.getAddress(), wallet.getBalance(), usdAmount);
    }

    @Transactional(readOnly = true)
    public String findWalletAddress() {
        return walletRepository.findWallet().getAddress();
    }

    @Transactional
    public void updateBalance(BigDecimal balance) {
        Wallet wallet = walletRepository.findWallet();
        wallet.setBalance(balance);
        walletRepository.save(wallet);
    }
}
