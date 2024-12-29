package com.alex.fiap.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "Login do usuário", example = "maria123")
    @NotBlank(message = "O login não pode ser vazio ou apenas espaços")
    private String login;

    @Schema(description = "Senha do usuário", example = "senhaSegura")
    @NotBlank(message = "A senha não pode ser vazia ou apenas espaços")
    private String senha;

}
