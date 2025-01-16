package com.fiap.gestao.restaurante.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SmartRestaurantException extends RuntimeException {

    HttpStatus status;

    public SmartRestaurantException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public SmartRestaurantException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
