package by.miner.mono.web.controller;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.service.UserProfitService;
import by.miner.mono.web.dto.UserProfitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping
    public void saveAlgorithmProfit(@RequestBody UserProfitDto userProfitDto, Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        userProfitService.saveUserProfit(user, userProfitDto);
    }
}
