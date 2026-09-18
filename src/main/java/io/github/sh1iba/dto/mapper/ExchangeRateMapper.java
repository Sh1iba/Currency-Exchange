package io.github.sh1iba.dto.mapper;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.model.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateMapper {

    private ExchangeRateMapper() {
    }

    public static ExchangeRateDto toDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(exchangeRate.getId(), exchangeRate.getBaseCurrencyId(),
                exchangeRate.getTargetCurrencyId(), exchangeRate.getRate());
    }

    public static List<ExchangeRateDto> listToDto(List<ExchangeRate> exchangeRateList) {
        List<ExchangeRateDto> exchangeRateDtoList = new ArrayList<>();
        for (ExchangeRate exchangeRate : exchangeRateList) {
            exchangeRateDtoList.add(toDto(exchangeRate));
        }
        return exchangeRateDtoList;
    }

    public static ExchangeRate toEntity(ExchangeRateDto exchangeRateDto) {
        return new ExchangeRate(exchangeRateDto.getBaseCurrencyId(), exchangeRateDto.getTargetCurrencyId(), exchangeRateDto.getRate());
    }
}
