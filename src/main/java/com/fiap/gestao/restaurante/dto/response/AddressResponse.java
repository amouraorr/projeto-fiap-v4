package com.fiap.gestao.restaurante.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddressResponse {

    @Schema(description = "ID do endereço.")
    private Long id;

    @Schema(description = "Nome da rua do endereço.")
    private String rua;

    @Schema(description = "Nome do bairro do endereço.")
    private String bairro;

    @Schema(description = "Número do endereço, se houver.")
    private String numero;

    @Schema(description = "Complemento do endereço, se houver.")
    private String complemento;

    @Schema(description = "Ponto de refência do endereço, se houver.")
    private String pontoDeReferencia;

    @Schema(description = "Nome da cidade do endereço.")
    private String cidade;

    @Schema(description = "Sigla do estado do endereço.")
    private String estado;

    @Schema(description = "Código de Endereçamento Postal (CEP) do endereço.")
    private String cep;

    @Schema(description = "data de criação.")
    private LocalDateTime criadoEm;

    @Schema(description = "Data de atualização.")
    private LocalDateTime atualizadoEm;

    @Schema(description = "usuário associado ao endereço")
    private User usuario;

    @Getter
    @Setter
    public static class User {
        @Schema(description = "Nome completo do usuário.")
        private String nome;

        @Schema(description = "Endereço de email do usuário.")
        private String email;
    }
}
