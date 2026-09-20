package io.github.sh1iba.dto.mapper;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExchangeRateMapper {

    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    @Mapping(source = "baseCurrencyId", target = "baseCurrency")
    @Mapping(source = "targetCurrencyId", target = "targetCurrency")
    ExchangeRateDto toDto(ExchangeRate exchangeRate);

    @Mapping(source = "baseCurrencyId", target = "baseCurrency")
    @Mapping(source = "targetCurrencyId", target = "targetCurrency")
    List<ExchangeRateDto> listToDto(List<ExchangeRate> exchangeRateList);

    @Mapping(source = "baseCurrency", target = "baseCurrencyId")
    @Mapping(source = "targetCurrency", target = "targetCurrencyId")
    ExchangeRate toEntity(ExchangeRateDto exchangeRateDto);
}
