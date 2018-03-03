package com.miner.mono.persistence.repository;

import com.miner.mono.persistence.model.UserShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface UserShareRepository extends JpaRepository<UserShare, Long>, UserShareRepositoryCustom {
    @Modifying
    @Query("update UserShare set share = 0, lastContributionDate = ?2 where id = ?1")
    void reset(long userId, LocalDateTime lastContributionDate);

    @Modifying
    @Query("update UserShare set share = share + ?2, lastContributionDate = ?3 where id = ?1")
    void contribute(long id, BigDecimal delta, LocalDateTime lastContributionDate);
}
