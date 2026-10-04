package io.github.sh1iba.service;

import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.dao.ExchangeRateDao;
import io.github.sh1iba.dao.ExchangeRateDaoImpl;

public class ExchangeService {
    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
    private final CurrencyDao currencyDao = new CurrencyDaoImpl();



}
