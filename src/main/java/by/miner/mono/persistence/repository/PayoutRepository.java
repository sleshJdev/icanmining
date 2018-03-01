package by.miner.mono.persistence.repository;

import by.miner.mono.persistence.model.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
}
