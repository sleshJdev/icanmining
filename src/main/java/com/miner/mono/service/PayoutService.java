package com.miner.mono.service;

import com.miner.mono.dto.PayoutDto;
import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.exception.InsufficientFundsException;
import com.miner.mono.persistence.model.ApplicationUser;
import com.miner.mono.persistence.model.Payout;
import com.miner.mono.persistence.repository.ApplicationUserRepository;
import com.miner.mono.persistence.repository.PayoutRepository;
import com.miner.mono.persistence.repository.UserShareRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.miner.mono.util.TimeUtils.utcNowDateTime;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
public class PayoutService {
    private final UserShareRepository userShareRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PayoutRepository payoutRepository;
    private final BigDecimal minPayout;
    private final BigDecimal fee;

    public PayoutService(UserShareRepository userShareRepository,
                         ApplicationUserRepository applicationUserRepository,
                         PayoutRepository payoutRepository,
                         @Value("${app.core.min_payout}") BigDecimal minPayout,
                         @Value("${app.core.fee_percents}") BigDecimal feePercents) {
        this.userShareRepository = userShareRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.payoutRepository = payoutRepository;
        this.minPayout = minPayout;
        this.fee = feePercents.movePointLeft(2);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PayoutDto createPayout(long userId, BigDecimal amountBtc) {
        if (amountBtc.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException("Payout amount should be great than zero.");
        }
        if (amountBtc.compareTo(minPayout) < 0) {
            throw new InsufficientFundsException(
                    String.format(
                            "Insufficient funds. You need have minimal %f BTC to withdrawal.",
                            minPayout.doubleValue()));
        }
        UserProfitItem userProfitItem = userShareRepository.calculateUserProfit(userId);
        BigDecimal userProfit = ofNullable(userProfitItem).map(UserProfitItem::getProfit).orElse(BigDecimal.ZERO);
        if (amountBtc.compareTo(userProfit) > 0) {
            throw new InsufficientFundsException(
                    String.format(
                            "Insufficient funds. It is not enough funds for withdrawal %f BTC.",
                            amountBtc.doubleValue()));
        }
        BigDecimal feeAmount = amountBtc.multiply(fee);
        BigDecimal actualAmount = amountBtc.subtract(feeAmount);
        ApplicationUser user = applicationUserRepository.findOne(userId);
        Payout payout = payoutRepository.save(new Payout(user, actualAmount, feeAmount, utcNowDateTime()));
        return toDto(payout);
    }

    @Transactional
    public PayoutDto approvePayout(long payoutId) {
        Payout payout = payoutRepository.findOne(payoutId);
        payout.setCloseDate(utcNowDateTime());
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
        payout.setCloseDate(utcNowDateTime());
        Payout canceledPayout = payoutRepository.save(payout);
        return toDto(canceledPayout);
    }

    @Transactional(readOnly = true)
    public List<PayoutDto> findApprovalPendingPayouts() {
        List<Payout> payouts = payoutRepository.findApprovalPendingPayouts();
        return payouts.stream().map(this::toDto).collect(toList());
    }

    private PayoutDto toDto(Payout payout) {
        return new PayoutDto(
                payout.getId(),
                payout.getUser().getId(),
                payout.getAmount(),
                payout.getFee(),
                payout.getIssueDate(),
                payout.getCloseDate(),
                payout.isCanceled());
    }
}
