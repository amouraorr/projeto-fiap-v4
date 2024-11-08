package com.alex.fiap.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import jakarta.validation.Valid;
import java.util.Date;

@Entity

@Data

@Table(name = "users", schema = "fiap")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome do usuário, deve ter entre 2 e 100 caracteres")
    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Schema(description = "Email do usuário, deve ser um email válido")
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "O email deve ser válido")
    private String email;

    @Schema(description = "Login do usuário, deve ter entre 3 e 20 caracteres")
    @NotNull(message = "O login não pode ser nulo")
    @Size(min = 3, max = 20, message = "O login deve ter entre 3 e 20 caracteres")
    private String login;

    @Schema(description = "Senha do usuário, deve ter pelo menos 6 caracteres")
    @NotNull(message = "A senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    @Schema(description = "Data de criação do usuário")
    private Date data;

    @Embedded
    @Valid
    private Endereco endereco;


    @Schema(description = "Tipo de usuário: 'cliente' ou 'dono do restaurante'")
    //@NotBlank(message = "O tipo não pode ser vazio")
    @Pattern(regexp = "^(cliente|dono do restaurante)$", message = "O tipo deve ser 'cliente' ou 'dono do restaurante'")
    private String tipo;

}
