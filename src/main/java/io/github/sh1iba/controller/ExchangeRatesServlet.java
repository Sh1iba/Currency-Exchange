package io.github.sh1iba.controller;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.exception.IncorrectRequestException;
import io.github.sh1iba.exception.ObjectExistsException;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.service.ExchangeRateService;
import io.github.sh1iba.validation.CurrencyValidation;
import io.github.sh1iba.validation.ExchangeRateValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {
    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            List<ExchangeRateDto> exchangeRateDto = exchangeRateService.getAll();
            resp.setStatus(HttpServletResponse.SC_OK);
            gson.toJson(exchangeRateDto, resp.getWriter());
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            validateForm(req);
            String baseCurrencyCode = req.getParameter("baseCurrencyCode");
            String targetCurrencyCode = req.getParameter("targetCurrencyCode");
            ExchangeRateValidation.codeValidation(baseCurrencyCode);
            ExchangeRateValidation.codeValidation(targetCurrencyCode);
            BigDecimal rate;
            try {
                rate = new BigDecimal(req.getParameter("rate"));
            } catch (NumberFormatException e) {
                throw new IncorrectRequestException("Rate must be a valid number");
            }

            ExchangeRateDto exchangeRateDto = exchangeRateService.addExchangeRate(baseCurrencyCode, targetCurrencyCode, rate);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            gson.toJson(exchangeRateDto, resp.getWriter());

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

    private void validateForm(HttpServletRequest req) throws IncorrectRequestException {
        ExchangeRateValidation.formValidation(req, "baseCurrencyCode");
        ExchangeRateValidation.formValidation(req, "targetCurrencyCode");
        ExchangeRateValidation.formValidation(req, "rate");
    }

}
