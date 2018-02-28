package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItem;
import by.miner.mono.persistence.model.UserProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserProfitRepository extends JpaRepository<UserProfit, Long>, UserProfitRepositoryCustom {
    List<UserProfitItem> calculateUsersProfit();
}
