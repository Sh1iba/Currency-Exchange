package io.github.sh1iba.controller;

import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.exception.ObjectExistsException;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.exception.IncorrectRequestException;
import io.github.sh1iba.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends BaseServlet {

    private final CurrencyService currencyService = new CurrencyService();

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
        try {
            resp.setContentType("application/json");
            CurrencyDto currencyDto = getDataFromForm(req);
            currencyDto = currencyService.addCurrency(currencyDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            gson.toJson(currencyDto, resp.getWriter());
        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IncorrectRequestException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (ObjectExistsException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_CONFLICT);
        }
    }

    private CurrencyDto getDataFromForm(HttpServletRequest req) throws IncorrectRequestException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");
        if (name == null || name.isBlank()) {
            throw new IncorrectRequestException("The required 'name' form field is missing");
        }
        if (code == null || code.isBlank()) {
            throw new IncorrectRequestException("The required 'code' form field is missing");
        }
        if (sign == null || sign.isBlank()) {
            throw new IncorrectRequestException("The required 'sign' form field is missing");
        }
        return new CurrencyDto(code, name, sign);
    }
}
