package com.icanmining.persistence.repository;

import com.icanmining.enums.Currency;
import com.icanmining.persistence.model.ExchangeRate;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
    ExchangeRate findByCurrency(Currency currency);
}
