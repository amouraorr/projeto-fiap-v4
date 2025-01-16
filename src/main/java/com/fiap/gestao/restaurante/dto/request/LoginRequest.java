package com.fiap.gestao.restaurante.dto.request;

import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "Login do usuário, deve ter entre 3 e 20 caracteres", example = "Seu login")
    @NotBlank(message = "O login não pode ser vazio ou apenas espaços")
    @Size(min = 3, max = 20, message = "O login deve ter entre 3 e 20 caracteres")
    private String login;

    @Schema(description = "Senha do usuário, deve ter pelo menos 6 caracteres", example = "Sua senha")
    @NotBlank(message = "A senha não pode ser vazia ou apenas espaços")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @Schema(description = "Tipo de usuário, deve ser 'cliente' ou 'proprietario'.",
            example = "proprietario"
    )
    private UserTypeEnum tipo;
}
