package by.miner.mono.web.controller;

import by.miner.mono.dto.ApplicationUserDto;
import by.miner.mono.dto.UserProfitItem;
import by.miner.mono.dto.UserProfitRequest;
import by.miner.mono.service.ApplicationUserService;
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
    private final ApplicationUserService applicationUserService;

    @Autowired
    public UserProfitController(UserProfitService userProfitService, ApplicationUserService applicationUserService) {
        this.userProfitService = userProfitService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/user")
    public UserProfitItem getUserProfit(Principal principal) {
        ApplicationUserDto user = applicationUserService.findByUsername(principal.getName());
        return userProfitService.calculateProfit(user.getId());
    }

    @PostMapping
    public void saveUserProfit(@RequestBody UserProfitRequest userProfitRequest, Principal principal) {
        ApplicationUserDto user = applicationUserService.findByUsername(principal.getName());
        userProfitService.saveUserProfit(user, userProfitRequest);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserProfitItem> getProfitInfo() {
        return userProfitService.calculateProfit();
    }
}
