package io.github.sh1iba.dto.mapper;

import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDto toDto(Currency currency);

    List<CurrencyDto> listToDto(List<Currency> currencies);

    Currency toEntity(CurrencyDto currencyDto);

}
