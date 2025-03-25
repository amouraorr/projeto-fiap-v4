package com.fiap.gestao.restaurante.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmartRestaurantExceptionTest {

    @Test
    @DisplayName("Deve criar SmartRestaurantException com mensagem e status")
    void shouldCreateSmartRestaurantExceptionWithMessageAndStatus() {
        String message = "Erro interno";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        SmartRestaurantException exception = new SmartRestaurantException(message, status);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }

    @Test
    @DisplayName("Deve criar SmartRestaurantException com mensagem, status e causa")
    void shouldCreateSmartRestaurantExceptionWithMessageStatusAndCause() {
        String message = "Erro interno";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Throwable cause = new RuntimeException("Causa do erro");

        SmartRestaurantException exception = new SmartRestaurantException(message, status, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatus());
        assertEquals(cause, exception.getCause());
    }
}