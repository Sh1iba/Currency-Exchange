package io.github.sh1iba.controller;

import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.exception.IncorrectRequestException;
import io.github.sh1iba.exception.ObjectExistsException;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.validation.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class ExchangeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String baseCurrencyCode = req.getParameter("from");
            String targetCurrencyCode = req.getParameter("to");
            String strAmount = req.getParameter("amount");
            parameterValidation(baseCurrencyCode, targetCurrencyCode, strAmount);
            BigDecimal amount = new BigDecimal(strAmount);


        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IncorrectRequestException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (ObjectExistsException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_CONFLICT);
        } catch (ObjectNotFoundException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void parameterValidation(String baseCurrencyCode, String targetCurrencyCode, String strAmount) {
        Validation.formValidation(baseCurrencyCode, "from");
        Validation.formValidation(targetCurrencyCode, "to");
        Validation.formValidation(strAmount, "amount");

        Validation.codeValidation(baseCurrencyCode);
        Validation.codeValidation(targetCurrencyCode);
        Validation.rateValidation(strAmount);
    }
}
