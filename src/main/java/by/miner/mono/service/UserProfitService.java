package by.miner.mono.service;

import by.miner.mono.dto.UserProfitItem;
import by.miner.mono.dto.UserProfitRequest;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.persistence.repository.UserProfitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

@Service
public class UserProfitService {
    private final UserProfitRepository userProfitRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public UserProfitService(UserProfitRepository userProfitRepository, ApplicationUserRepository applicationUserRepository) {
        this.userProfitRepository = userProfitRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional
    public void saveUserProfit(ApplicationUser user, UserProfitRequest userProfitRequest) {
        userProfitRepository.save(new by.miner.mono.persistence.model.UserProfit(
                applicationUserRepository.findOne(user.getId()),
                userProfitRequest.getProfit(),
                userProfitRequest.getMiningInterval(),
                OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()
        ));
    }

    @Transactional(readOnly = true)
    public Collection<UserProfitItem> calculateProfit() {
        return userProfitRepository.calculateUsersProfit();
    }

    @Transactional(readOnly = true)
    public UserProfitItem calculateProfit(long id) {
        return userProfitRepository.calculateUserProfit(id);
    }
}
