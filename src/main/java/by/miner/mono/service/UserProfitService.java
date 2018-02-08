package by.miner.mono.service;

import by.miner.mono.dto.UserProfitDto;
import by.miner.mono.dto.UserProfitItemInfoDto;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.UserProfit;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.persistence.repository.UserProfitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

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
                userProfitDto.getMiningInterval(),
                OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()
        ));
    }

    @Transactional(readOnly = true)
    public Collection<UserProfitItemInfoDto> calculateProfit(LocalDateTime from, LocalDateTime to) {
        return userProfitRepository.calculateUsersProfit(from, to);
    }

    @Transactional(readOnly = true)
    public UserProfitItemInfoDto calculateProfit(long id) {
        return userProfitRepository.calculateUserProfit(id);
    }
}
