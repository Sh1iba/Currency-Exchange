package io.github.sh1iba.service;

import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.dao.ExchangeRateDao;
import io.github.sh1iba.dao.ExchangeRateDaoImpl;
import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.dto.mapper.ExchangeRateMapper;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.model.Currency;
import io.github.sh1iba.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
    private final CurrencyDao currencyDao = new CurrencyDaoImpl();

    public List<ExchangeRateDto> getAll() {
        return ExchangeRateMapper.INSTANCE.listToDto(exchangeRateDao.getAll());
    }

    public ExchangeRateDto getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRate exchangeRate = exchangeRateDao.get(baseCurrencyCode, targetCurrencyCode)
                .orElseThrow(() -> new ObjectNotFoundException("The exchange rate for the pair has not been found"));
        return ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
    }

    public ExchangeRateDto addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        Currency base = currencyDao.get(baseCurrencyCode).orElseThrow(() ->
                new ObjectNotFoundException("The currency " + baseCurrencyCode + " was not found"));
        Currency target = currencyDao.get(targetCurrencyCode).orElseThrow(() ->
                new ObjectNotFoundException("The currency " + targetCurrencyCode + " was not found"));
        ExchangeRate exchangeRate = exchangeRateDao.insert(new ExchangeRate(base, target, rate));
        return ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
    }

    public ExchangeRateDto updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        ExchangeRate exchangeRate = exchangeRateDao.get(baseCurrencyCode, targetCurrencyCode)
                .orElseThrow(() -> new ObjectNotFoundException("The exchange rate for the pair has not been found"));
        exchangeRate = exchangeRateDao.update(exchangeRate, rate);
        return ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
    }

}
