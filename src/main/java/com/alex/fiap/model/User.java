package com.alex.fiap.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "users", schema = "fiap")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do usuário")
    private String nome;

    @Schema(description = "Email do usuário")
    private String email;

    @Schema(description = "Login do usuário")
    private String login;

    @Schema(description = "Senha do usuário")
    private String senha;

    @Schema(description = "Data de criação do usuário")
    private Date data;

    @Embedded
    @Valid
    @Schema(description = "Endereço do usuário")
    private Endereco endereco;

    @Schema(description = "Tipo do usuário, deve ser 'cliente' ou 'dono do restaurante'")
    private String tipo;
}
