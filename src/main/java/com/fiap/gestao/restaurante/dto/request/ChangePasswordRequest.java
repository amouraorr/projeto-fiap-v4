package com.fiap.gestao.restaurante.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @Schema(description = "Nova senha do usuário", example = "SuaSenhaSegura")
    @NotBlank(message = "A nova senha não pode ser vazia ou apenas espaços")
    private String newPassword;
}