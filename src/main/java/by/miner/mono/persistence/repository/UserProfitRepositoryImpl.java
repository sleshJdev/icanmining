package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static by.miner.mono.persistence.model.UserProfit.USER_PROFIT_MAPPING_NAME;

public class UserProfitRepositoryImpl implements UserProfitRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<UserProfitItem> calculateUsersProfit() {
        return em.createNativeQuery(CALCULATE_USERS_PROFIT_QUERY, USER_PROFIT_MAPPING_NAME)
                .getResultList();
    }

    @Override
    public UserProfitItem calculateUserProfit(long id) {
        return (UserProfitItem) em.createNativeQuery(CALCULATE_USER_PROFIT_QUERY, USER_PROFIT_MAPPING_NAME)
                .setParameter("id", id)
                .getSingleResult();
    }

}
