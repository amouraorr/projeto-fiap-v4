package com.fiap.gestao.restaurante.dto.response;

import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    @Schema(description = "ID do login.")
    private Long id;

    @Schema(description = "login.")
    private String login;

    @Schema(description = "Tipo de usuário, que pode ser 'CLIENTE' ou 'PROPRIETARIO'.")
    private UserTypeEnum tipo;
}
