package com.icanmining.web.controller;

import com.icanmining.dto.ApplicationUserDto;
import com.icanmining.dto.UserProfitItem;
import com.icanmining.service.ApplicationUserService;
import com.icanmining.service.UserShareService;
import com.icanmining.dto.UserShareRequest;
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
