package com.bressan.backend.security;

import com.bressan.backend.exception.ErrorResponse;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Construye respuestas HTTP 403 para denegaciones detectadas por los filtros.
 */
@Component
public class AccessDeniedHandlerJwt implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public AccessDeniedHandlerJwt(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    /**
     * Devuelve el error 403 con el formato JSON común de la API.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException{
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
        errorResponse.setMessage("No tiene permisos para acceder a este recurso");
        errorResponse.setPath(request.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}