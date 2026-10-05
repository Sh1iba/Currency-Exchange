package io.github.sh1iba.service;

import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.dao.ExchangeRateDao;
import io.github.sh1iba.dao.ExchangeRateDaoImpl;
import io.github.sh1iba.dto.ExchangeDto;
import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.dto.mapper.ExchangeRateMapper;
import io.github.sh1iba.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public class ExchangeService {
    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
    private final CurrencyDao currencyDao = new CurrencyDaoImpl();

    public ExchangeDto exchange(String baseCurrency, String targetCurrency, BigDecimal amount) {
        Optional<ExchangeDto> res = directExchange(baseCurrency, targetCurrency, amount);
        if (res.isPresent()) {
            return res.get();
        }

        return null;
    }

    private Optional<ExchangeDto> directExchange(String baseCurrency, String targetCurrency, BigDecimal amount) {
        ExchangeRate exchangeRate = null;
        ExchangeDto exchangeDto = null;
        Optional<ExchangeRate> res = exchangeRateDao.get(baseCurrency, targetCurrency);
        if (res.isPresent()) {
            exchangeRate = res.get();
            ExchangeRateDto exchangeRateDto = ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
            BigDecimal convertedAmount = amount.multiply(exchangeRateDto.getRate());
            exchangeDto = new ExchangeDto(exchangeRateDto.getBaseCurrency(), exchangeRateDto.getTargetCurrency(),
                    exchangeRateDto.getRate(), amount, convertedAmount);
        }
        return Optional.ofNullable(exchangeDto);
    }



}
