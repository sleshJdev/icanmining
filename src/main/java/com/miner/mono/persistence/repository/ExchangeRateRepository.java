package com.miner.mono.persistence.repository;

import com.miner.mono.enums.Currency;
import com.miner.mono.persistence.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
    ExchangeRate findByCurrency(Currency currency);
}
