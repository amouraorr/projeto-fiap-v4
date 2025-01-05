package com.alex.fiap.model;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    @Column(name = "endereco_rua")
    private String rua;

    @Column(name = "endereco_cidade")
    private String cidade;

    @Column(name = "endereco_estado")
    private String estado;

    @Column(name = "endereco_cep")
    private String cep;

}
