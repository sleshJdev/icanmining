package com.icanmining.service;

import com.icanmining.dto.ApplicationUserDto;
import com.icanmining.persistence.model.ApplicationUser;
import com.icanmining.persistence.model.UserShare;
import com.icanmining.persistence.repository.ApplicationUserRepository;
import com.icanmining.persistence.repository.UserShareRepository;
import com.icanmining.dto.UserProfitItem;
import com.icanmining.dto.UserShareRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.icanmining.util.TimeUtils.utcNowDateTime;

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
        BigDecimal share = shareRequest.getProfit();
        BigDecimal delta = share.multiply(miningInterval).divide(DAY_SECONDS, 15, RoundingMode.HALF_DOWN);
        userShareRepository.save(new UserShare(user, delta, utcNowDateTime()));
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
