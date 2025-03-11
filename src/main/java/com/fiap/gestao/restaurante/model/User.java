package com.fiap.gestao.restaurante.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
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
    @JsonManagedReference
    private Set<Address> enderecos;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_login", nullable = false)
    private Login login;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserTypeEnum userType;

    @OneToMany(mappedBy = "proprietario", fetch = FetchType.EAGER) // Esta linha para o relacionamento com Restaurant
    @JsonManagedReference
    private Set<Restaurant> restaurantes;
}
