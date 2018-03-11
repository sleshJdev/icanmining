package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.dto.UserShareRequest;
import com.miner.mono.persistence.model.ApplicationUser;
import com.miner.mono.persistence.model.UserShare;
import com.miner.mono.persistence.repository.ApplicationUserRepository;
import com.miner.mono.persistence.repository.UserShareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserShareService {
    private static final BigDecimal DAY_SECONDS = BigDecimal.valueOf(TimeUnit.DAYS.toSeconds(1));
    private final UserShareRepository userShareRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public UserShareService(UserShareRepository userShareRepository, ApplicationUserRepository applicationUserRepository) {
        this.userShareRepository = userShareRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional
    public void contribute(ApplicationUserDto userDto, UserShareRequest shareRequest) {
        ApplicationUser user = applicationUserRepository.findOne(userDto.getId());
        BigDecimal miningInterval = shareRequest.getMiningInterval();
        BigDecimal share = shareRequest.getShare();
        BigDecimal delta = share.multiply(miningInterval).divide(DAY_SECONDS, 15, RoundingMode.HALF_DOWN);
        LocalDateTime now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        userShareRepository.save(new UserShare(user, delta, now));
    }

    @Transactional(readOnly = true)
    public List<UserProfitItem> calculateProfits() {
        return userShareRepository.calculateUsersProfit();
    }

    @Transactional(readOnly = true)
    public UserProfitItem calculateProfit(long userId) {
        return userShareRepository.calculateUserProfit(userId);
    }
}
