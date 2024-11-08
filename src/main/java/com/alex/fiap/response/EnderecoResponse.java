package com.alex.fiap.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponse {

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

}