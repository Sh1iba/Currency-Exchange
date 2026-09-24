package io.github.sh1iba.controller;

import io.github.sh1iba.dto.CurrencyDto;
import io.github.sh1iba.exception.IncorrectRequestException;
import io.github.sh1iba.exception.ObjectNotFoundException;
import io.github.sh1iba.exception.DatabaseException;
import io.github.sh1iba.service.CurrencyService;
import io.github.sh1iba.validation.CurrencyValidation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends BaseServlet {

    private final CurrencyService currencyService = new CurrencyService();
    private final CurrencyValidation validation = new CurrencyValidation();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String path = req.getPathInfo();
            validation.pathValidation(path);
            String code = path.substring(1);

            validation.codeValidation(code);
            CurrencyDto currencyDto = currencyService.getCurrencyByCode(code);
            resp.setStatus(HttpServletResponse.SC_OK);
            gson.toJson(currencyDto, resp.getWriter());

        } catch (DatabaseException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (IncorrectRequestException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (ObjectNotFoundException e) {
            writeErrorMessage(resp, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
