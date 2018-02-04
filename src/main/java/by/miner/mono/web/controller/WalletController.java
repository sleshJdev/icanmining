package by.miner.mono.web.controller;

import by.miner.mono.dto.WalletDto;
import by.miner.mono.persistence.model.Wallet;
import by.miner.mono.persistence.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public WalletDto getBitcoinAddress() {
        Iterable<Wallet> addresses = walletRepository.findAll();
        Wallet address = addresses.iterator().next();
        return new WalletDto(address.getAddress(), address.getBalance());
    }
}
