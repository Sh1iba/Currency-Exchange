package io.github.sh1iba.controller;

import com.google.gson.Gson;
import io.github.sh1iba.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected final Gson gson = new Gson();

    protected void writeErrorMessage(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message);
        gson.toJson(errorResponseDto, resp.getWriter());
    }
}
