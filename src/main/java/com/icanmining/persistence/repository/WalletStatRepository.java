package com.icanmining.persistence.repository;

import com.icanmining.persistence.model.WalletStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletStatRepository extends JpaRepository<WalletStat, Long> {
    @Query("select ws from  WalletStat ws where ws.date = (select max(date) from WalletStat)")
    WalletStat findLastStat();
}
