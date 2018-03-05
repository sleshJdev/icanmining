package com.miner.mono.persistence.repository;

import com.miner.mono.persistence.model.Wallet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    default Wallet findWallet() {
        return findAll().iterator().next();
    }

    @Modifying(clearAutomatically = true)
    @Query("update Wallet set balance = balance - ?1, withdrawnBtc = withdrawnBtc + ?1 where id = 1")
    void withdrawal(BigDecimal amountBtc);
}
