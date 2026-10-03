package io.github.sh1iba.controller;

import com.google.gson.Gson;
import io.github.sh1iba.dto.ErrorResponseDto;
import io.github.sh1iba.exception.IncorrectRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class BaseServlet extends HttpServlet {
    private static final String METHOD_PATCH = "PATCH";
    protected final Gson gson = new Gson();

    protected void writeErrorMessage(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(message);
        gson.toJson(errorResponseDto, resp.getWriter());
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (METHOD_PATCH.equals(req.getMethod())) {
            doPatch(req, resp);
            return;
        }
        super.service(req, resp);
    }

    protected Map<String, String> parseFromForm(HttpServletRequest req) throws IOException {
        Map<String, String> body = new HashMap<>();
        try {
            String form = new String(req.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            for (String pair : form.split("&")) {
                int eqIndex = pair.indexOf("=");
                String key = pair.substring(0, eqIndex);
                String value = pair.substring(eqIndex + 1);
                body.put(key, value);
            }
        } catch (Exception e) {
            throw new IncorrectRequestException("Data transmission error in the request body");
        }
        return body;
    }

}
