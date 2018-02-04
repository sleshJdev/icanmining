package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItemInfoDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

public class UserProfitRepositoryImpl implements UserProfitRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<UserProfitItemInfoDto> calculateProfit(LocalDateTime from, LocalDateTime to) {
        return em.createNativeQuery(CALCULATE_PROFIT_QUERY, "by.miner.mono.dto.UserProfitItemInfoDto")
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }
}
