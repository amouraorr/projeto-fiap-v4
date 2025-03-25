package com.fiap.gestao.restaurante.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class MenuItemRequest {

    @Schema(description = "Nome do item do cardápio, deve ter entre 2 e 150 caracteres", example = "Pizza Margherita")
    @NotBlank(message = "O nome do item não pode ser vazio ou apenas espaços")
    @Size(min = 2, max = 150, message = "O nome do item deve ter entre 2 e 150 caracteres")
    private String nome;

    @Schema(description = "Descrição do item do cardápio, pode ter até 500 caracteres", example = "Uma deliciosa pizza com molho de tomate e queijo.")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @Schema(description = "Preço do item do cardápio, deve ser um valor válido", example = "29.90")
    @NotNull(message = "O preço é obrigatório")
    private BigDecimal preco;

    @Schema(description = "Disponibilidade local do item, true se disponível, false caso contrário", example = "true")
    @NotNull(message = "A disponibilidade local é obrigatória")
    private Boolean disponibilidadeLocal;

    @Schema(description = "Caminho da foto do item do cardápio, pode ter até 250 caracteres", example = "/imagens/pizza.jpg")
    @Size(max = 250, message = "O caminho da foto deve ter no máximo 250 caracteres")
    private String caminhoFoto;
}