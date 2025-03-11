package com.fiap.gestao.restaurante.dto.response;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemResponse {

    @Schema(description = "ID do item do cardápio", example = "1")
    private Long id;

    @Schema(description = "Nome do item do cardápio", example = "Pizza Margherita")
    private String nome;

    @Schema(description = "Descrição do item do cardápio", example = "Uma deliciosa pizza com molho de tomate e queijo.")
    private String descricao;

    @Schema(description = "Preço do item do cardápio", example = "29.90")
    private BigDecimal preco;

    @Schema(description = "Disponibilidade local do item", example = "true")
    private Boolean disponibilidadeLocal;

    @Schema(description = "Caminho da foto do item do cardápio", example = "/imagens/pizza.jpg")
    private String caminhoFoto;

    @Schema(description = "Mensagem de status da operação", example = "Item criado com sucesso")
    private String mensagemStatus;
}