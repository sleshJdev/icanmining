package com.miner.mono.web.controller;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.dto.UserShareRequest;
import com.miner.mono.service.ApplicationUserService;
import com.miner.mono.service.UserShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/api/profit")
public class UserProfitController {
    private final UserShareService userShareService;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public UserProfitController(UserShareService userShareService, ApplicationUserService applicationUserService) {
        this.userShareService = userShareService;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/user")
    public UserProfitItem getUserProfit(Principal principal) {
        ApplicationUserDto user = applicationUserService.findByUsername(principal.getName());
        return userShareService.calculateProfit(user.getId());
    }

    @PostMapping
    public void saveUserProfit(@RequestBody UserShareRequest userShareRequest, Principal principal) {
        ApplicationUserDto user = applicationUserService.findByUsername(principal.getName());
        userShareService.contribute(user, userShareRequest);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserProfitItem> getProfitInfo() {
        return userShareService.calculateProfits();
    }
}
