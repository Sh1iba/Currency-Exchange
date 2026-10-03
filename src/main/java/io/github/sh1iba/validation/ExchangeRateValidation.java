package io.github.sh1iba.validation;

import io.github.sh1iba.exception.IncorrectRequestException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class ExchangeRateValidation {
    public static void pathValidation(String path) throws IncorrectRequestException {
        if (path == null || path.equals("/")) {
            throw new IncorrectRequestException("The currency pair codes are missing from the address");
        }
    }

    public static void pairOfCodesValidation(String code, int length) throws IncorrectRequestException {
        if (code.length() != length || !code.equals(code.toUpperCase())) {
            throw new IncorrectRequestException("Invalid exchange rate format. Exchange rate must be exactly " +
                    length + " uppercase letters. Example : 'USDRUB'");
        }
    }

    public static void codeValidation(String code) throws IncorrectRequestException {
        if (code.length() != 3 || !code.equals(code.toUpperCase())) {
            throw new IncorrectRequestException("Invalid currency code format. Currency code must be " +
                    "exactly 3 uppercase letters. Example : 'USD'");
        }
    }

    public static void formValidation(HttpServletRequest req, String value) {
        if (req.getParameter(value) == null || req.getParameter(value).isBlank()) {
            throw new IncorrectRequestException("The required '" + value + "' form field is missing");
        }
    }

    public static void formValidation(Map<String, String> reqBody, String value) {
        if (reqBody.get(value) == null || reqBody.get(value).isBlank()) {
            throw new IncorrectRequestException("The required '" + value + "' form field is missing");
        }
    }
}
