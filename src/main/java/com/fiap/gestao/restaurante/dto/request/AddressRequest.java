package com.fiap.gestao.restaurante.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    @Schema(description = "Nome da rua do endereço", example = "Rua Ana")
    @NotBlank(message = "O nome da rua não pode ser vazio ou apenas espaços")
    private String rua;

    @Schema(description = "Nome do bairro do endereço.", example = "Bairro nobre")
    @NotBlank(message = "O nome do bairro não pode ser vazio ou apenas espaços")
    private String bairro;

    @Schema(description = "Número do endereço, se houver.", example = "500")
    private String numero;

    @Schema(description = "Complemento do endereço, se houver.", example = "apartamento 25")
    private String complemento;

    @Schema(description = "Ponto de refência do endereço, se houver.", example = "condomínio morada nobre")
    private String pontoDeReferencia;

    @Schema(description = "Nome da cidade do endereço", example = "São Paulo")
    @NotBlank(message = "O nome da cidade não pode ser vazio ou apenas espaços")
    private String cidade;

    @Schema(description = "Sigla do estado do endereço", example = "SP")
    @Pattern(regexp = "^[a-zA-Z]{2}$", message = "A Sigla do estado tem que ter 2 dígitos")
    @NotNull
    private String estado;

    @Schema(description = "CEP do endereço", example = "12345678")
    @Pattern(regexp = "^[0-9]{8}$", message = "O CEP deve ter 8 dígitos")
    @NotNull
    private String cep;

    @Schema(description = "Id do usuário", example = "1")
    @NotNull(message = "O ID do usuário é obrigatório")
    private Long idUsuario;
}
