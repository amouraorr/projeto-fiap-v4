package com.fiap.gestao.restaurante.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = "usuario")
@Entity
@Table(name = "endereco")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String rua;

    @Column(length = 50)
    private String numero;

    @Column(length = 50, nullable = false)
    private String bairro;

    @Column(length = 150)
    private String complemento;

    @Column(name = "ponto_de_referencia", length = 250)
    private String pontoDeReferencia;

    @Column(length = 150, nullable = false)
    private String cidade;

    @Column(length = 2, nullable = false)
    private String estado;

    @Column(length = 8, nullable = false)
    private String cep;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;

}
