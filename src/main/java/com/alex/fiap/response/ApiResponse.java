package com.alex.fiap.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Classe que representa a resposta da API.
 *
 * @param <T> Tipo dos dados retornados na resposta.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Adiciona esta anotação
public class ApiResponse<T> {
    private final T data;
    private final String error;

    public ApiResponse(T data) {
        this.data = data;
        this.error = null; // Inicializa como null se não houver erro
    }

    public ApiResponse(String error) {
        this.data = null; // Inicializa como null se houver erro
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
                "data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
