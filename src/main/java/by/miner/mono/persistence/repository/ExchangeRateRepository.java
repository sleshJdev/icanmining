package by.miner.mono.persistence.repository;

import by.miner.mono.enums.Currency;
import by.miner.mono.persistence.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
    ExchangeRate findByCurrency(Currency currency);
}
