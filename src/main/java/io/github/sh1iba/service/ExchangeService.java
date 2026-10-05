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
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeService {
    private final ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
    private final CurrencyDao currencyDao = new CurrencyDaoImpl();

    public ExchangeDto exchange(String baseCurrency, String targetCurrency, BigDecimal amount) {
        Optional<ExchangeDto> res = directExchange(baseCurrency, targetCurrency, amount);
        if (res.isPresent()) {
            return res.get();
        }
        res = reverseExchange(baseCurrency, targetCurrency, amount);
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
            BigDecimal convertedAmount = amount.multiply(exchangeRateDto.getRate()).setScale(2, RoundingMode.HALF_UP);
            exchangeDto = new ExchangeDto(exchangeRateDto.getBaseCurrency(), exchangeRateDto.getTargetCurrency(),
                    exchangeRateDto.getRate(), amount, convertedAmount);
        }
        return Optional.ofNullable(exchangeDto);
    }

    private Optional<ExchangeDto> reverseExchange(String baseCurrency, String targetCurrency, BigDecimal amount) {
        ExchangeRate exchangeRate = null;
        ExchangeDto exchangeDto = null;
        Optional<ExchangeRate> res = exchangeRateDao.get(targetCurrency, baseCurrency);
        if (res.isPresent()) {
            exchangeRate = res.get();
            ExchangeRateDto exchangeRateDto = ExchangeRateMapper.INSTANCE.toDto(exchangeRate);
            BigDecimal convertedAmount = amount.divide(exchangeRateDto.getRate(), 2, RoundingMode.HALF_UP);
            BigDecimal rate = new BigDecimal(1).divide(exchangeRateDto.getRate(), 6, RoundingMode.HALF_UP);
            exchangeDto = new ExchangeDto(exchangeRateDto.getTargetCurrency(), exchangeRateDto.getBaseCurrency(),
                    rate, amount, convertedAmount);
        }
        return Optional.ofNullable(exchangeDto);
    }

}
