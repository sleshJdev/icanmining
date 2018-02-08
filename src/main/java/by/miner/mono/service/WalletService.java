package by.miner.mono.service;

import by.miner.mono.dto.WalletDto;
import by.miner.mono.persistence.model.Wallet;
import by.miner.mono.persistence.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional(readOnly = true)
    public WalletDto findWallet() {
        Wallet wallet = walletRepository.findAll().iterator().next();
        return new WalletDto(wallet.getAddress(), wallet.getBalance());
    }
}
