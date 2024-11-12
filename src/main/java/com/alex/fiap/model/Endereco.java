package com.alex.fiap.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Data
public class Endereco {

    @Column(name = "endereco_rua")
    private String rua;

    @Column(name = "endereco_cidade")
    private String cidade;

    @Column(name = "endereco_estado")
    private String estado;

    @Column(name = "endereco_cep")
    private String cep;

}
