package com.fiap.gestao.restaurante.model;

import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@ToString(exclude = "senha")
@Entity
@Table(name = "credenciais")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String login;

    @Column(length = 150, nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_usuario", nullable = false)
    private UserTypeEnum tipo;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;
}
