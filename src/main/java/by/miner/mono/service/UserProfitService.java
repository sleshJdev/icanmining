package by.miner.mono.service;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.UserProfit;
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

    @Autowired
    public UserProfitService(UserProfitRepository userProfitRepository) {
        this.userProfitRepository = userProfitRepository;
    }

    @Transactional
    public void saveUserProfit(ApplicationUser user, UserProfitDto userProfitDto) {
        userProfitRepository.save(new UserProfit(
                user,
                userProfitDto.getProfit(),
                userProfitDto.getAlgorithmType(),
                userProfitDto.getMiningPeriod(),
                OffsetDateTime.now(ZoneId.of("UTC")).toLocalDateTime()
        ));
    }
}
