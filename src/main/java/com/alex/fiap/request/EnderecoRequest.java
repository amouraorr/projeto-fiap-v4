package com.alex.fiap.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequest {

    @Schema(description = "Nome da rua do endereço", example = "Nome da rua")
    @NotBlank(message = "O nome da rua não pode ser vazio ou apenas espaços")
    private String rua;

    @Schema(description = "Nome da cidade do endereço", example = "Nome da cidade")
    @NotBlank(message = "O nome da cidade não pode ser vazio ou apenas espaços")
    private String cidade;

    @Schema(description = "Nome do estado do endereço", example = "Nome do estado")
    @NotBlank(message = "O nome do estado não pode ser vazio ou apenas espaços")
    private String estado;

    @Schema(description = "CEP do endereço", example = "12345-678")
    @NotBlank(message = "O cep não pode ser vazio ou apenas espaços")
    private String cep;
}
