package com.fiap.gestao.restaurante.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @Schema(description = "Nome do usuário, deve ter entre 2 e 100 caracteres", example = "Seu nome completo")
    @NotBlank(message = "O nome não pode ser vazio ou apenas espaços")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Schema(description = "Email do usuário, deve ser um email válido", example = "email@example.com")
    @NotBlank(message = "O email não pode ser vazio ou apenas espaços")
    @Email(message = "O email deve ser válido")
    private String email;

}
