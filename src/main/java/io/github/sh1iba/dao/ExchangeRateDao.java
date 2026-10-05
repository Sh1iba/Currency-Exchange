package io.github.sh1iba.dao;

import io.github.sh1iba.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateDao {

    List<ExchangeRate> getAll();

    Optional<ExchangeRate> get(String baseCurrencyCode, String targetCurrencyCode);

    ExchangeRate insert(ExchangeRate exchangeRate);

    ExchangeRate update(ExchangeRate exchangeRate, BigDecimal rate);
}
