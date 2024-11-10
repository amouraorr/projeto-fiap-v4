package com.alex.fiap.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@Table(name = "users", schema = "fiap")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private Date data;
    private Endereco endereco;
    private String tipo;

}
