package io.github.sh1iba.controller;

import com.google.gson.Gson;
import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.dto.ErrorResponseDto;
import io.github.sh1iba.exception.CurrencyCodeExistsException;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.exception.MissingFormFieldException;
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
            gson.toJson(currencyDtoList, resp.getWriter());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            CurrencyDto currencyDto = getDataFromForm(req);
            currencyDto = currencyService.addCurrency(currencyDto);
            gson.toJson(currencyDto, resp.getWriter());
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (MissingFormFieldException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (CurrencyCodeExistsException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_CONFLICT);
        }
    }

    private void writeErrorMessage(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message);
        gson.toJson(errorResponseDto, resp.getWriter());
    }

    private CurrencyDto getDataFromForm(HttpServletRequest req) throws MissingFormFieldException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        if (name == null || name.isBlank()) {
            throw new MissingFormFieldException("The required 'name' form field is missing");
        }
        if (code == null || code.isBlank()) {
            throw new MissingFormFieldException("The required 'code' form field is missing");
        }
        if (sign == null || sign.isBlank()) {
            throw new MissingFormFieldException("The required 'sign' form field is missing");
        }
        return new CurrencyDto(code, name, sign);
    }
}
