package io.github.sh1iba.service;

import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.dto.CurrencyMapper;
import io.github.sh1iba.model.Currency;

import java.util.List;

public class CurrencyService {

    private final CurrencyDao currencyDao = new CurrencyDaoImpl();

    public List<CurrencyDto> getAll() {
        return CurrencyMapper.listToDto(currencyDao.getAll());
    }

    public CurrencyDto getCurrencyByCode(String code) {
        return CurrencyMapper.toDto(currencyDao.get(code));
    }

    public CurrencyDto addCurrency(CurrencyDto currencyDto) {
        Currency currency = CurrencyMapper.toEntity(currencyDto);
        return CurrencyMapper.toDto(currencyDao.insert(currency));
    }

}
