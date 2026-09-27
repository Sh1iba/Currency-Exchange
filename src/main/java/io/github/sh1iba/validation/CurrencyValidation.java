package io.github.sh1iba.validation;

import io.github.sh1iba.exception.IncorrectRequestException;
import jakarta.servlet.http.HttpServletRequest;

public class CurrencyValidation {

    public static void pathValidation(String path) throws IncorrectRequestException {
        if (path == null || path.equals("/")) {
            throw new IncorrectRequestException("The currency code is missing from the address");
        }
    }

    public static void codeValidation(String code) throws IncorrectRequestException {
        if (code.length() != 3 || !code.equals(code.toUpperCase())) {
            throw new IncorrectRequestException("Invalid currency code format. Currency code must be " +
                    "exactly 3 uppercase letters. Example : 'USD'");
        }
    }

    public static void formValidation(HttpServletRequest req, String value){
        if (req.getParameter(value) == null || req.getParameter(value).isBlank()){
            throw new IncorrectRequestException("The required '" + value + "' form field is missing");
        }
    }


}
