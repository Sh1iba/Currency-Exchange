package io.github.sh1iba.service;

import io.github.sh1iba.dao.ExchangeRateDao;
import io.github.sh1iba.dao.ExchangeRateDaoImpl;
import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.dto.mapper.ExchangeRateMapper;
import io.github.sh1iba.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {

    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();

    public List<ExchangeRateDto> getAll() {
        return ExchangeRateMapper.INSTANCE.listToDto(exchangeRateDao.getAll());
    }

    public ExchangeRateDto getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        return ExchangeRateMapper.INSTANCE.toDto(exchangeRateDao.get(baseCurrencyCode, targetCurrencyCode));
    }

    public ExchangeRateDto addExchangeRate(ExchangeRateDto exchangeRateDto) {
        ExchangeRate exchangeRate = ExchangeRateMapper.INSTANCE.toEntity(exchangeRateDto);
        exchangeRate = exchangeRateDao.insert(exchangeRate);
        return ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
    }

    public ExchangeRateDto updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        ExchangeRate exchangeRate = exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, rate);
        return ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
    }

}
