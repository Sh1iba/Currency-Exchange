package io.github.sh1iba.controller;

import com.google.gson.Gson;
import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.dto.ErrorResponseDto;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<CurrencyDto> currencyDtoList = currencyService.getAll();
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            gson.toJson(currencyDtoList, resp.getWriter());
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void writeErrorMessage(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message);
        gson.toJson(errorResponseDto, resp.getWriter());
    }
}
