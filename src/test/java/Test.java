import io.github.sh1iba.dao.CurrencyDao;
import io.github.sh1iba.dao.CurrencyDaoImpl;
import io.github.sh1iba.dao.ExchangeRateDao;
import io.github.sh1iba.dao.ExchangeRateDaoImpl;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.model.Currency;
import io.github.sh1iba.model.ExchangeRate;
import io.github.sh1iba.utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    private static void connect() {
        try (var connection = DatabaseConnection.getConnection()) {
            System.out.println("Connection to database is successful");
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred", e);
        }
    }

    public static void main(String[] args) {
        //getCurrencyByCode("USD");
        //insertCurrency(new Currency("CZK", "Czech Koruna", "Kč"));
        //getAllExchangeRate();
        //getExchangeRateByCodes("USD","EUR");
        //addExchangeRate(new ExchangeRate(25, 22, new BigDecimal("48.65")));
        updateExchangeRate("USD", "TRY", new BigDecimal("48.66"));
    }

    private static void getAllCurrencies() {
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        List<Currency> list = new ArrayList<>();
        list = currencyDao.getAll();
        for (Currency c : list) {
            System.out.println(c);
        }
    }

    private static void getCurrencyByCode(String code) {
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        Currency currency = currencyDao.get(code);
        System.out.println(currency);
    }

    private static void insertCurrency(Currency currency) {
        CurrencyDao currencyDao = new CurrencyDaoImpl();
        currency = currencyDao.insert(currency);
        System.out.println(currency);
    }

    private static void getAllExchangeRate() {
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
        List<ExchangeRate> list = new ArrayList<>();
        list = exchangeRateDao.getAll();
        for (ExchangeRate e : list) {
            System.out.println(e);
        }
    }

    private static void getExchangeRateByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
        ExchangeRate exchangeRate = exchangeRateDao.get(baseCurrencyCode, targetCurrencyCode);
        System.out.println(exchangeRate);
    }

    private static void addExchangeRate(ExchangeRate exchangeRate) {
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
        exchangeRate = exchangeRateDao.insert(exchangeRate);
        System.out.println(exchangeRate);
    }

    private static void updateExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        ExchangeRate exchangeRate = null;
        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl();
        exchangeRate = exchangeRateDao.update(baseCurrencyCode, targetCurrencyCode, rate);
        System.out.println(exchangeRate);
    }


}
