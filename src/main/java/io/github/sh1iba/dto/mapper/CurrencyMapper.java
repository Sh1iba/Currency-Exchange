package io.github.sh1iba.dto.mapper;

import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.model.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CurrencyMapper {

    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    @Mapping(source = "fullName", target = "name")
    CurrencyDto toDto(Currency currency);

    @Mapping(source = "fullName", target = "name")
    List<CurrencyDto> listToDto(List<Currency> currencies);

    @Mapping(source = "name", target = "fullName")
    Currency toEntity(CurrencyDto currencyDto);

}
