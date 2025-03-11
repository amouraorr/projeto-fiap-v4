package com.fiap.gestao.restaurante.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class RestaurantRequest {

    @Schema(description = "Nome do restaurante, deve ter entre 2 e 150 caracteres", example = "Nome do Restaurante")
    @NotBlank(message = "O nome do restaurante não pode ser vazio ou apenas espaços")
    @Size(min = 2, max = 150, message = "O nome do restaurante deve ter entre 2 e 150 caracteres")
    private String nome;

    @Schema(description = "Endereço do restaurante, deve ter entre 2 e 150 caracteres", example = "Rua Exemplo, 123")
    @NotBlank(message = "O endereço não pode ser vazio ou apenas espaços")
    @Size(min = 2, max = 150, message = "O endereço deve ter entre 2 e 150 caracteres")
    private String endereco;

    @Schema(description = "Tipo de cozinha do restaurante, deve ter entre 2 e 100 caracteres", example = "Italiana")
    @NotBlank(message = "O tipo de cozinha não pode ser vazio ou apenas espaços")
    @Size(min = 2, max = 100, message = "O tipo de cozinha deve ter entre 2 e 100 caracteres")
    private String tipoCozinha;

    @Schema(description = "Horário de funcionamento do restaurante, deve ter entre 1 e 25 caracteres", example = "08:00 - 22:00")
    @NotBlank(message = "O horário de funcionamento não pode ser vazio ou apenas espaços")
    @Size(min = 1, max = 25, message = "O horário de funcionamento deve ter entre 1 e 25 caracteres")
    private String horarioFuncionamento;

    @Schema(description = "ID do proprietário do restaurante, deve ser válido", example = "1")
    @NotNull(message = "O ID do proprietário é obrigatório")
    private Long idProprietario;

}