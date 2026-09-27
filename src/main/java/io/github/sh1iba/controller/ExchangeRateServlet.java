package io.github.sh1iba.controller;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.exception.IncorrectRequestException;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.service.ExchangeRateService;
import io.github.sh1iba.validation.ExchangeRateValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String path = req.getPathInfo();
            ExchangeRateValidation.pathValidation(path);
            String pairOfCodes = path.substring(1);
            ExchangeRateValidation.pairOfCodesValidation(pairOfCodes);
            String baseCode = pairOfCodes.substring(0, 3);
            String targetCode = pairOfCodes.substring(3, 6);
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
}
