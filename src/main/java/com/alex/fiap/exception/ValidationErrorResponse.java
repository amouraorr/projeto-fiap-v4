package com.alex.fiap.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private String field;
    private String message;

    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
