package io.github.sh1iba.controller;

import io.github.sh1iba.dto.ExchangeRateDto;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.service.ExchangeRateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
}
