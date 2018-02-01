package by.miner.mono.persistence.repository;

import by.miner.mono.persistence.model.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
}
