package com.fiap.gestao.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"enderecos","login"})
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 150, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Address> enderecos;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_login", nullable = false)
    private Login login;
}
