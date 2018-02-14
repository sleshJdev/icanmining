package by.miner.mono.web.controller;

import by.miner.mono.dto.UserProfitDto;
import by.miner.mono.dto.UserProfitItemInfoDto;
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
    public UserProfitItemInfoDto getUserProfit(Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        return userProfitService.calculateProfit(user.getId());
    }

    @PostMapping
    public void saveUserProfit(@RequestBody UserProfitDto userProfitDto, Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        userProfitService.saveUserProfit(user, userProfitDto);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Collection<UserProfitItemInfoDto> getProfitInfo() {
        return userProfitService.calculateProfit();
    }
}
