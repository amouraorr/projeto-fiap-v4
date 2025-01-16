package com.fiap.gestao.restaurante.exception;

import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        LOGGER.error("Erro de validação: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SmartRestaurantException.class)
    public ResponseEntity<ApiResponse<Void>> handleSmartRestaurantException(SmartRestaurantException ex) {
        LOGGER.error("Erro interno:", ex);
        return ResponseEntity.status(ex.status).body(new ApiResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Throwable throwable) {
        LOGGER.error("Erro interno:", throwable);
        var message = throwable.getMessage() != null ? throwable.getMessage() : "Erro interno!";
        return ResponseEntity.internalServerError().body(new ApiResponse<>(message));
    }
}

