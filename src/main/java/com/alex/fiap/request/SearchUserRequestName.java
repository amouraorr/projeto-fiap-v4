package com.alex.fiap.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserRequestName {

    @Schema(description = "Nome do usuário a ser buscado", example = "Maria")
    @NotBlank(message = "O nome não pode ser vazio ou apenas espaços")
    private String nome;
}