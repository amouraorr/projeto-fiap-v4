package com.alex.fiap.request;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class EnderecoRequest {

    private String rua;
    private String cidade;
    private String estado;
    private String cep;
}