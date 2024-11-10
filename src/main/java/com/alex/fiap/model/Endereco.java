package com.alex.fiap.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Endereco {

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

}
