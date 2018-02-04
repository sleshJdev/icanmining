package by.miner.mono.web.controller;

import by.miner.mono.enums.ProfitInterval;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.service.UserProfitService;
import by.miner.mono.dto.UserProfitDto;
import by.miner.mono.dto.UserProfitItemInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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

    @PostMapping
    public void saveUserProfit(@RequestBody UserProfitDto userProfitDto, Principal principal) {
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        userProfitService.saveUserProfit(user, userProfitDto);
    }

    @GetMapping
    public Collection<UserProfitItemInfoDto> getProfitInfo(@RequestParam ProfitInterval interval) {
        LocalDateTime now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime from = interval == ProfitInterval.year
                ? now.minusYears(1) : interval == ProfitInterval.month
                ? now.minusMonths(1) : now.minusDays(1);
        return userProfitService.calculateProfit(from, now);
    }
}
