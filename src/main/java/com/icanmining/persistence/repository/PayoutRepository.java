package com.icanmining.persistence.repository;

import com.icanmining.persistence.model.Payout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
    @Modifying
    @Query("update Payout set canceled = true, closeDate = ?2 where id = ?1")
    void cancelPayout(long id, LocalDateTime closeDate);

    @Query("select p from Payout p where p.canceled = false and p.closeDate is null")
    List<Payout> findApprovalPendingPayouts();
}
