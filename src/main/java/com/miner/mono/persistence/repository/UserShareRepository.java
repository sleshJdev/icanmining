package com.miner.mono.persistence.repository;

import com.miner.mono.dto.UserProfitItem;
import com.miner.mono.persistence.model.UserShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserShareRepository extends JpaRepository<UserShare, Long> {
    String USER_SHARE_MAPPING_NAME = "UserShareItem";
    String CALCULATE_USERS_PROFIT_QUERY_NAME = "UserShareRepository.calculateUsersProfit";
    String CALCULATE_USER_PROFIT_QUERY_NAME = "UserShareRepository.calculateUserProfit";
    String CALCULATE_USERS_PROFIT_QUERY =
            "SELECT u.user_id AS id, " +
            "       a.username AS username, " +
            "       u.last_contribution_date AS last_contribution_date, " +
            "       CASE " +
            "           WHEN ts.total_share = 0 THEN 0 " +
            "           ELSE ((u.share / ts.total_share) * w.balance) - ifnull(p.user_total_payout, 0) " +
            "       END AS profit " +
            "FROM " +
            "  (SELECT us.user_id AS user_id, " +
            "          sum(us.share) AS share, " +
            "          max(us.last_contribution_date) AS last_contribution_date " +
            "   FROM user_share AS us " +
            "   GROUP BY us.user_id) AS u " +
            "INNER JOIN application_user AS a ON u.user_id = a.id " +
            "LEFT JOIN " +
            "  (SELECT user_id AS user_id, " +
            "          sum(amount) AS user_total_payout " +
            "   FROM payout " +
            "   WHERE canceled = FALSE " +
            "   GROUP BY user_id) p ON p.user_id = u.user_id, " +
            "  (SELECT SUM(share) AS total_share " +
            "   FROM user_share) AS ts, " +
            "  (SELECT balance AS balance " +
            "   FROM wallet " +
            "   WHERE id = 1) AS w";
    String CALCULATE_USER_PROFIT_QUERY =
            "SELECT t.* FROM (" + CALCULATE_USERS_PROFIT_QUERY + ") AS t WHERE t.id = ?1";

    @Query(name = CALCULATE_USERS_PROFIT_QUERY_NAME, nativeQuery = true)
    List<UserProfitItem> calculateUsersProfit();

    @Query(name = CALCULATE_USER_PROFIT_QUERY_NAME, nativeQuery = true)
    UserProfitItem calculateUserProfit(long id);
}
