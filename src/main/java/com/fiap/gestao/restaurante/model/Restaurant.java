package com.fiap.gestao.restaurante.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"proprietario"})
@Entity
@Table(name = "restaurante")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 150, nullable = false)
    private String endereco;

    @Column(length = 100, nullable = false)
    private String tipoCozinha;

    @Column(length = 25, nullable = false)
    private String horarioFuncionamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proprietario", nullable = false)
    @JsonBackReference
    private User proprietario;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

}