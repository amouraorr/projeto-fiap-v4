package com.alex.fiap.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Data
public class Endereco {

    @Schema(description = "Nome da rua do endereço")
    @Column(name = "endereco_rua")
    @NotNull(message = "O nome da rua não pode ser nulo")
    private String rua;

    @Schema(description = "Nome da cidade do endereço")
    @Column(name = "endereco_cidade")
    @NotNull(message = "O nome da cidade não pode ser nulo")
    private String cidade;

    @Schema(description = "Nome do estado do endereço")
    @Column(name = "endereco_estado")
    @NotNull(message = "O nome da estado não pode ser nulo")
    private String estado;

    @Schema(description = "Cep do endereço, somente números")
    @Column(name = "endereco_cep")
    @NotNull(message = "O cep não pode ser nulo")
    private String cep;

}
