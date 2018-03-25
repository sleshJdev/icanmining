package com.icanmining.persistence.repository;

import com.icanmining.persistence.model.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    long WALLET_ID = 1L;

    default Wallet findWallet() {
        return findOne(WALLET_ID);
    }
}
