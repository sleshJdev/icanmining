package by.miner.mono.service;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.UserProfit;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.persistence.repository.UserProfitRepository;
import by.miner.mono.web.dto.UserProfitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
public class UserProfitService {
    private final UserProfitRepository userProfitRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserProfitService(UserProfitRepository userProfitRepository, ApplicationUserRepository applicationUserRepository) {
        this.userProfitRepository = userProfitRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional
    public void saveUserProfit(ApplicationUser user, UserProfitDto userProfitDto) {
        userProfitRepository.save(new UserProfit(
                applicationUserRepository.findOne(user.getId()),
                userProfitDto.getProfit(),
                userProfitDto.getAlgorithmType(),
                userProfitDto.getMiningInterval(),
                OffsetDateTime.now(ZoneId.of("UTC")).toLocalDateTime()
        ));
    }
}
