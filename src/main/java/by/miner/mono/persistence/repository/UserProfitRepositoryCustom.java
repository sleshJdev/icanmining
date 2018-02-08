package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItemInfoDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UserProfitRepositoryCustom {

    String CALCULATE_USERS_PROFIT_QUERY =
            "SELECT t1.user_id id, " +
            "       t1.username username, " +
            "       ((t1.user_profit / t2.total_profit) * t3.balance) profit, " +
            "           (SELECT count(*) > 0 " +
            "           FROM user_profit " +
            "           WHERE user_profit.user_id = t1.user_id " +
            "           AND issue_date > utc_timestamp - INTERVAL 1 MINUTE) active " +
            "FROM " +
            "  (SELECT user_id, " +
            "          username, " +
            "          SUM(profit) user_profit " +
            "   FROM user_profit " +
            "   INNER JOIN application_user a ON user_profit.user_id = a.id " +
            "   WHERE issue_date >= :from AND issue_date <= :to " +
            "   GROUP BY user_id) AS t1, " +
            "  (SELECT SUM(profit) total_profit " +
            "   FROM user_profit) AS t2, " +
            "  (SELECT balance " +
            "   FROM wallet " +
            "   WHERE id = 1) AS t3";

    String CALCULATE_USER_PROFIT_QUERY =
            "SELECT t.* FROM (" +
            "   SELECT t1.user_id id, " +
            "           t1.username username, " +
            "           ((t1.user_profit / t2.total_profit) * t3.balance) profit, " +
            "               (SELECT count(*) > 0 " +
            "               FROM user_profit " +
            "               WHERE user_profit.user_id = t1.user_id " +
            "               AND issue_date > utc_timestamp - INTERVAL 1 MINUTE) active " +
            "   FROM " +
            "       (SELECT user_id, " +
            "           username, " +
            "           SUM(profit) user_profit " +
            "       FROM user_profit " +
            "       INNER JOIN application_user a ON user_profit.user_id = a.id " +
            "       GROUP BY user_id) AS t1, " +
            "       (SELECT SUM(profit) total_profit " +
            "       FROM user_profit) AS t2, " +
            "       (SELECT balance " +
            "       FROM wallet " +
            "       WHERE id = 1) AS t3" +
            ") AS t WHERE t.id = :id";

    List<UserProfitItemInfoDto> calculateUsersProfit(LocalDateTime from, LocalDateTime to);

    UserProfitItemInfoDto calculateUserProfit(long id);
}
