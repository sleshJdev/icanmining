package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.PayoutDto;
import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.persistence.model.ApplicationUser;
import com.miner.mono.persistence.model.Payout;
import com.miner.mono.persistence.repository.ApplicationUserRepository;
import com.miner.mono.persistence.repository.PayoutRepository;
import com.miner.mono.persistence.repository.UserShareRepository;
import com.miner.mono.persistence.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayoutService {
    private final UserShareRepository userShareRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PayoutRepository payoutRepository;
    private final WalletRepository walletRepository;

    public PayoutService(UserShareRepository userShareRepository,
                         ApplicationUserRepository applicationUserRepository,
                         PayoutRepository payoutRepository,
                         WalletRepository walletRepository) {
        this.userShareRepository = userShareRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.payoutRepository = payoutRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public PayoutDto createPayout(ApplicationUserDto userDto) {
        ApplicationUser user = applicationUserRepository.findOne(userDto.getId());
        LocalDateTime issueDate = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        Payout payout = payoutRepository.save(new Payout(user, BigDecimal.ZERO, issueDate));
        return toDto(payout);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PayoutDto approvePayout(long payoutId) {
        LocalDateTime now = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
        Payout payout = payoutRepository.findOne(payoutId);
        Long userId = payout.getUser().getId();
        UserProfitItem userProfitItem = userShareRepository.calculateUserProfit(userId);
        userShareRepository.reset(userId, now);
        payout.setAmount(userProfitItem.getProfit());
        payout.setCloseDate(now);
        Payout approvedPayout = payoutRepository.save(payout);
        return toDto(approvedPayout);
    }

    @Transactional(readOnly = true)
    public PayoutDto getPayout(long id) {
        Payout payout = payoutRepository.findOne(id);
        return toDto(payout);
    }

    @Transactional
    public PayoutDto cancelPayout(long id) {
        Payout payout = payoutRepository.findOne(id);
        payout.setCanceled(true);
        payout.setCloseDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        Payout canceledPayout = payoutRepository.save(payout);
        return toDto(canceledPayout);
    }

    private PayoutDto toDto(Payout payout) {
        return new PayoutDto(
                payout.getId(),
                payout.getUser().getId(),
                payout.getAmount(),
                payout.getIssueDate(),
                payout.getCloseDate(),
                payout.isCanceled());
    }

    @Transactional(readOnly = true)
    public List<PayoutDto> findApprovalPendingPayouts() {
        List<Payout> payouts = payoutRepository.findApprovalPendingPayouts();
        return payouts.stream().map(this::toDto).collect(Collectors.toList());
    }
}
