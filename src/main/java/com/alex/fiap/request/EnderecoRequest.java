package com.alex.fiap.request;


import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoRequest {

    @NotNull(message = "O nome da rua não pode ser nulo")
    private String rua;

    @NotNull(message = "O nome da cidade não pode ser nulo")
    private String cidade;

    @NotNull(message = "O nome do estado não pode ser nulo")
    private String estado;

    @NotNull(message = "O cep não pode ser nulo")
    private String cep;

}
