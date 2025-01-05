package com.alex.fiap.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.Valid;

@Getter
@Setter
public class UserRequest {

    @Schema(description = "Nome do usuário, deve ter entre 2 e 100 caracteres", example = "Seu nome completo")
    @NotBlank(message = "O nome não pode ser vazio ou apenas espaços")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Schema(description = "Email do usuário, deve ser um email válido", example = "email@example.com")
    @NotBlank(message = "O email não pode ser vazio ou apenas espaços")
    @Email(message = "O email deve ser válido")
    private String email;

    @Schema(description = "Login do usuário, deve ter entre 3 e 20 caracteres", example = "Seu login")
    @NotBlank(message = "O login não pode ser vazio ou apenas espaços")
    @Size(min = 3, max = 20, message = "O login deve ter entre 3 e 20 caracteres")
    private String login;

    @Schema(description = "Senha do usuário, deve ter pelo menos 6 caracteres", example = "Sua senha")
    @NotBlank(message = "A senha não pode ser vazia ou apenas espaços")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @Embedded
    @Valid
    @Schema(description = "Endereço do usuário")
    private AddressRequest endereco;

    @Schema(description = "Tipo de usuário, deve ser 'cliente' ou 'dono do restaurante'.",
            example = "Tipo de usuário, deve ser 'cliente' ou 'dono do restaurante", // Exemplo de valor que pode ser fornecido
            required = true // Indica que este campo é obrigatório
    )
    @NotBlank(message = "O tipo não pode ser vazio ou apenas espaços")
    @Pattern(regexp = "^(cliente|dono do restaurante)$", message = "O tipo deve ser 'cliente' ou 'dono do restaurante'")
    private String tipo;
}
