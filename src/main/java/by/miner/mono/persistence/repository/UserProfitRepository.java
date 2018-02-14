package by.miner.mono.persistence.repository;

import by.miner.mono.dto.UserProfitItemInfoDto;
import by.miner.mono.persistence.model.UserProfit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserProfitRepository extends CrudRepository<UserProfit, Long>, UserProfitRepositoryCustom {
    List<UserProfitItemInfoDto> calculateUsersProfit();
}
