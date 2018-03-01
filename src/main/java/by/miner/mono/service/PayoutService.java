package by.miner.mono.service;

import by.miner.mono.dto.ApplicationUserDto;
import by.miner.mono.dto.PayoutDto;
import by.miner.mono.persistence.model.Payout;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.persistence.repository.PayoutRepository;
import by.miner.mono.persistence.repository.UserProfitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class PayoutService {
    private final UserProfitRepository userProfitRepository;
    private final ApplicationUserRepository applicationUserRepository;
    private final PayoutRepository payoutRepository;

    public PayoutService(UserProfitRepository userProfitRepository,
                         ApplicationUserRepository applicationUserRepository,
                         PayoutRepository payoutRepository) {
        this.userProfitRepository = userProfitRepository;
        this.applicationUserRepository = applicationUserRepository;
        this.payoutRepository = payoutRepository;
    }

    @Transactional
    public PayoutDto createPayout(ApplicationUserDto userDto) {
        Payout payout = payoutRepository.save(new Payout(
                applicationUserRepository.findOne(userDto.getId()),
                userProfitRepository.calculateUserProfit(userDto.getId()).getProfit(),
                ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime()
        ));
        return toDto(payout);
    }


    @Transactional
    public void approvePayout(long payoutId) {
        Payout payout = payoutRepository.findOne(payoutId);
        payout.setApprovalDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        payoutRepository.save(payout);
    }

    @Transactional(readOnly = true)
    public PayoutDto getPayout(long id) {
        Payout payout = payoutRepository.findOne(id);
        return toDto(payout);
    }

    private PayoutDto toDto(Payout payout) {
        return new PayoutDto(
                payout.getId(),
                payout.getUser().getId(),
                payout.getAmount(),
                payout.getIssueDate(),
                payout.getApprovalDate());
    }
}
