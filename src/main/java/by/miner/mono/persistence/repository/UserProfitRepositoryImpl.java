package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItemInfoDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

public class UserProfitRepositoryImpl implements UserProfitRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<UserProfitItemInfoDto> calculateProfit(LocalDateTime from, LocalDateTime to) {
        return em.createNativeQuery(CALCULATE_PROFIT_QUERY, "by.miner.mono.dto.UserProfitItemInfoDto")
                .setParameter("from", Date.from(from.toInstant(ZoneOffset.UTC)), TemporalType.TIMESTAMP)
                .setParameter("to", Date.from(to.toInstant(ZoneOffset.UTC)), TemporalType.TIMESTAMP)
                .getResultList();
    }
}
