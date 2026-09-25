package io.github.sh1iba.dto.mapper;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.model.ExchangeRate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ExchangeRateMapper {

    ExchangeRateMapper INSTANCE = Mappers.getMapper(ExchangeRateMapper.class);

    ExchangeRateDto toDto(ExchangeRate exchangeRate);

    List<ExchangeRateDto> listToDto(List<ExchangeRate> exchangeRateList);

    ExchangeRate toEntity(ExchangeRateDto exchangeRateDto);
}
