package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItem;
import by.miner.mono.persistence.model.ApplicationUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

import static by.miner.mono.persistence.model.UserProfit.USER_PROFIT_MAPPING_NAME;

public class UserProfitRepositoryImpl implements UserProfitRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    private final ApplicationUserRepository applicationUserRepository;

    public UserProfitRepositoryImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }


    @SuppressWarnings("unchecked")
    public List<UserProfitItem> calculateUsersProfit() {
        return em.createNativeQuery(CALCULATE_USERS_PROFIT_QUERY, USER_PROFIT_MAPPING_NAME)
                .getResultList();
    }

    @Override
    public UserProfitItem calculateUserProfit(long id) {
        //TODO: move to procedure?
        List list = em.createNativeQuery(CALCULATE_USER_PROFIT_QUERY, USER_PROFIT_MAPPING_NAME)
                .setParameter("id", id).getResultList();

        if (list.isEmpty()) {
            ApplicationUser user = applicationUserRepository.findOne(id);
            return new UserProfitItem(user.getId(), user.getUsername(), BigDecimal.ZERO, Boolean.FALSE);
        } else if (list.size() > 1) {
            throw new RuntimeException("Incorrect SQL result. One record expected.");
        }

        return (UserProfitItem) list.get(0);
    }

}
