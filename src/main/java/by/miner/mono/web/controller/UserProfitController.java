package by.miner.mono.web.controller;

import by.miner.mono.dto.UserProfitRequest;
import by.miner.mono.dto.UserProfitItem;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.service.UserProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/profit")
public class UserProfitController {
    private final UserProfitService userProfitService;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserProfitController(UserProfitService userProfitService, ApplicationUserRepository applicationUserRepository) {
        this.userProfitService = userProfitService;
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping("/user")
    public UserProfitItem getUserProfit(Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        return userProfitService.calculateProfit(user.getId());
    }

    @PostMapping
    public void saveUserProfit(@RequestBody UserProfitRequest userProfitRequest, Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        userProfitService.saveUserProfit(user, userProfitRequest);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserProfitItem> getProfitInfo() {
        return userProfitService.calculateProfit();
    }
}
