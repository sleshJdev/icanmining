package com.miner.mono.persistence.repository;

import com.miner.mono.dto.UserProfitItem;

import java.util.List;

public interface UserShareRepositoryCustom {

    String CALCULATE_USERS_PROFIT_QUERY =
            "SELECT " +
            "  t1.user_id                                              id, " +
            "  t1.username                                             username, " +
            "  t1.active                                               active, " +
            "  CASE WHEN t2.total_share = 0 " +
            "    THEN 0 " +
            "  ELSE (t1.share / t2.total_share) * t3.total_balance END profit " +
            "FROM " +
            "  (SELECT " +
            "     us.user_id, " +
            "     a.username, " +
            "     us.share, " +
            "     us.last_contribution_date > utc_timestamp - INTERVAL 5 MINUTE active " +
            "   FROM user_share AS us " +
            "     INNER JOIN application_user AS a " +
            "       ON us.user_id = a.id) AS t1, " +
            "  (SELECT SUM(share) total_share " +
            "   FROM user_share) AS t2, " +
            "  (SELECT (balance + withdrawn_btc - p.total_payout) AS total_balance " +
            "   FROM wallet, " +
            "     (SELECT ifnull(sum(amount), 0) AS total_payout " +
            "      FROM payout " +
            "      WHERE close_date IS NOT NULL " +
            "            AND canceled = FALSE) AS p " +
            "   WHERE id = 1) AS t3";

    String CALCULATE_USER_PROFIT_QUERY =
            "SELECT t.* FROM (" +
                CALCULATE_USERS_PROFIT_QUERY +
            ") AS t WHERE t.id = :id";

    List<UserProfitItem> calculateUsersProfit();

    UserProfitItem calculateUserProfit(long id);
}
