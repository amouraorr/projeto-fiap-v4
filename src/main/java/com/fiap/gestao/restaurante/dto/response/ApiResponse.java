package com.fiap.gestao.restaurante.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Classe que representa a resposta da API.
 *
 * @param <T> Tipo dos dados retornados na resposta.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final T data;
    private final String error;

    public ApiResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public ApiResponse(String error) {
        this.data = null;
        this.error = error;
    }

    public ApiResponse(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data.sql=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
