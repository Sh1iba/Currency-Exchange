package io.github.sh1iba.validation;

import io.github.sh1iba.exception.IncorrectRequestException;

public class ExchangeRateValidation {
    public static void pathValidation(String path) throws IncorrectRequestException {
        if (path == null || path.equals("/")) {
            throw new IncorrectRequestException("The currency pair codes are missing from the address");
        }
    }

    public static void pairOfCodesValidation(String code) throws IncorrectRequestException {
        if (code.length() != 6 || !code.equals(code.toUpperCase())) {
            throw new IncorrectRequestException("Invalid exchange rate format. Exchange rate must be " +
                    "exactly 6 uppercase letters. Example : 'USDRUB'");
        }
    }
}
