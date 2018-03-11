package com.miner.mono.persistence.repository;

import com.miner.mono.persistence.model.WalletStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletStatRepository extends JpaRepository<WalletStat, Long> {
    @Query("select ws from  WalletStat ws where ws.date = (select max(date) from WalletStat)")
    WalletStat findLastStat();
}
