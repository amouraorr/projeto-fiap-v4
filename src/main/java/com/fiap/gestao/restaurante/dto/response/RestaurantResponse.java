package com.fiap.gestao.restaurante.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestaurantResponse {

    @Schema(description = "Identificador único do restaurante.")
    private Long id;

    @Schema(description = "Nome do restaurante.")
    private String nome;

    @Schema(description = "Endereço do restaurante.")
    private String endereco;

    @Schema(description = "Tipo de cozinha do restaurante.")
    private String tipoCozinha;

    @Schema(description = "Horário de funcionamento do restaurante.")
    private String horarioFuncionamento;

    @Schema(description = "Data de criação do restaurante.")
    private LocalDateTime criadoEm;

    @Schema(description = "Data de atualização do restaurante.")
    private LocalDateTime atualizadoEm;

    @Schema(description = "Informações do proprietário do restaurante.")
    private UserResponse proprietario;
}
