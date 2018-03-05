package com.miner.mono.persistence.repository;

import com.miner.mono.dto.UserProfitItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.miner.mono.persistence.model.UserShare.USER_SHARE_MAPPING_NAME;

public class UserShareRepositoryImpl implements UserShareRepositoryCustom {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<UserProfitItem> calculateUsersProfit() {
        return em.createNativeQuery(CALCULATE_USERS_PROFIT_QUERY, USER_SHARE_MAPPING_NAME)
                .getResultList();
    }

    @Override
    public UserProfitItem calculateUserProfit(long id) {
        try {
            UserProfitItem userProfitItem = (UserProfitItem) em.createNativeQuery(
                    CALCULATE_USER_PROFIT_QUERY, USER_SHARE_MAPPING_NAME)
                    .setParameter("id", id).getSingleResult();
            return userProfitItem;
        } catch (NoResultException e) {
            logger.warn(String.format("User[id=%d] has not profit yet, return null", id), e);
            return null;
        }
    }
}
