package com.alex.fiap.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    @Schema(description = "Nome da rua do endereço.")
    private String rua;

    @Schema(description = "Nome da cidade do endereço.")
    private String cidade;

    @Schema(description = "Nome do estado do endereço.")
    private String estado;

    @Schema(description = "Código de Endereçamento Postal (CEP) do endereço.")
    private String cep;
}
