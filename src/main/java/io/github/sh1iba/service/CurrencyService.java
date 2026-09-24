package io.github.sh1iba.service;

import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.dto.mapper.CurrencyMapper;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.model.Currency;

import java.util.List;

public class CurrencyService {

    private final CurrencyDao currencyDao = new CurrencyDaoImpl();

    public List<CurrencyDto> getAll() {
        return CurrencyMapper.INSTANCE.listToDto(currencyDao.getAll());
    }

    public CurrencyDto getCurrencyByCode(String code) {
        Currency currency = currencyDao.get(code).orElseThrow(() -> new ObjectNotFoundException("The currency was not found"));
        return CurrencyMapper.INSTANCE.toDto(currency);
    }

    public CurrencyDto addCurrency(CurrencyDto currencyDto) {
        Currency currency = CurrencyMapper.INSTANCE.toEntity(currencyDto);
        return CurrencyMapper.INSTANCE.toDto(currencyDao.insert(currency));
    }

}
