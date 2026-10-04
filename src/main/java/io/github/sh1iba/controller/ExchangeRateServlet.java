package io.github.sh1iba.controller;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.exception.IncorrectRequestException;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.service.ExchangeRateService;
import io.github.sh1iba.validation.Validation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();
    private static final int CURRENCY_CODE_LENGTH = 3;
    private static final int CURRENCY_PAIR_CODES_LENGTH = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String path = req.getPathInfo();
            Validation.pathValidation(path, PAIR_CODE_MESSAGE);
            String pairOfCodes = path.substring(1);
            Validation.pairOfCodesValidation(pairOfCodes);
            String baseCode = pairOfCodes.substring(0, CURRENCY_CODE_LENGTH);
            String targetCode = pairOfCodes.substring(CURRENCY_CODE_LENGTH, CURRENCY_PAIR_CODES_LENGTH);
            ExchangeRateDto exchangeRateDto = exchangeRateService.getExchangeRateByCodes(baseCode, targetCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            gson.toJson(exchangeRateDto, resp.getWriter());
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IncorrectRequestException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (ObjectNotFoundException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String path = req.getPathInfo();
            Validation.pathValidation(path, PAIR_CODE_MESSAGE);
            String pairOfCodes = path.substring(1);
            Validation.pairOfCodesValidation(pairOfCodes);
            String baseCode = pairOfCodes.substring(0, CURRENCY_CODE_LENGTH);
            String targetCode = pairOfCodes.substring(CURRENCY_CODE_LENGTH, CURRENCY_PAIR_CODES_LENGTH);

            Map<String, String> reqBody = parseFromForm(req);
            String strRate = reqBody.get("rate");
            Validation.formValidation(strRate, "rate");
            Validation.rateValidation(strRate);
            BigDecimal rate = new BigDecimal(strRate);

            ExchangeRateDto exchangeRateDto = exchangeRateService.updateExchangeRate(baseCode, targetCode, rate);
            resp.setStatus(HttpServletResponse.SC_OK);
            gson.toJson(exchangeRateDto, resp.getWriter());
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IncorrectRequestException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (ObjectNotFoundException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
