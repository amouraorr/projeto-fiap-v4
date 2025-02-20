package com.fiap.gestao.restaurante.exception;

import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve lidar com SmartRestaurantException")
    void shouldHandleSmartRestaurantException() {
        SmartRestaurantException exception = new SmartRestaurantException("Erro interno", HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<ApiResponse<Void>> response = globalExceptionHandler.handleSmartRestaurantException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro interno", response.getBody().getError());
    }

    @Test
    @DisplayName("Deve lidar com exceções gerais")
    void shouldHandleGeneralException() {
        Throwable throwable = new RuntimeException("Erro inesperado");

        ResponseEntity<ApiResponse<Void>> response = globalExceptionHandler.handleGeneralException(throwable);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro inesperado", response.getBody().getError());     }

    @Test
    @DisplayName("Deve lidar com MethodArgumentNotValidException")
    void shouldHandleMethodArgumentNotValidException() {
        // Simula o BindingResult com um erro de campo
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        // Cria um MethodParameter real a partir de um método existente
        MethodParameter methodParameter = new MethodParameter(
                this.getClass().getDeclaredMethods()[0], // Pega o primeiro método declarado na classe
                -1 // Índice do parâmetro, -1 se não for relevante
        );

        // Cria a exceção simulada com um MethodParameter válido
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        // Chama o método de tratamento
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);

        // Verifica o status e o corpo da resposta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("defaultMessage", response.getBody().get("field"));
    }
}
