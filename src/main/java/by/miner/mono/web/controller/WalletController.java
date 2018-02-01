package by.miner.mono.web.controller;

import by.miner.mono.persistence.model.Wallet;
import by.miner.mono.persistence.repository.WalletRepository;
import by.miner.mono.web.dto.WalletDto;
import by.miner.mono.web.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WalletController {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping("/wallet")
    public WalletDto getBitcoinAddress() {
        Iterable<Wallet> addresses = walletRepository.findAll();
        Wallet address = addresses.iterator().next();
        return new WalletDto(address.getAddress());
    }

}
