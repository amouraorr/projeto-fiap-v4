package com.alex.fiap.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    @Schema(description = "Identificador único do usuário.")
    private Long id;

    @Schema(description = "Nome completo do usuário.")
    private String nome;

    @Schema(description = "Endereço de email do usuário.")
    private String email;

    @Schema(description = "Tipo de usuário, que pode ser 'cliente' ou 'dono do restaurante'.")
    private String tipo;

    @Schema(description = "Informações de endereço do usuário.")
    private AddressResponse endereco;
}
