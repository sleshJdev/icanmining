package by.miner.mono.web.controller;

import by.miner.mono.dto.WalletDto;
import by.miner.mono.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/address")
    public String getWalletAddress() {
        return walletService.findWalletAddress();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public WalletDto getWalletDetails() {
        return walletService.findWallet();
    }
}
