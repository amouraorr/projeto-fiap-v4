package com.fiap.gestao.restaurante.repository;

import com.fiap.gestao.restaurante.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByLoginAndSenha(String login, String senha);
}
