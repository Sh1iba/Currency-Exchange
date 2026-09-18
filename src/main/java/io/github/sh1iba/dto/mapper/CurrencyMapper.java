package io.github.sh1iba.dto.mapper;

import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.model.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyMapper {

    private CurrencyMapper() {
    }

    public static CurrencyDto toDto(Currency currency) {
        return new CurrencyDto(currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign());
    }

    public static List<CurrencyDto> listToDto(List<Currency> currencies) {
        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        for(Currency currency : currencies){
            currencyDtoList.add(toDto(currency));
        }
        return currencyDtoList;
    }

    public static Currency toEntity(CurrencyDto currencyDto) {
        return new Currency(currencyDto.getCode(), currencyDto.getFullName(), currencyDto.getSign());
    }

}
