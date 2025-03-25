package com.fiap.gestao.restaurante.repository;

import com.fiap.gestao.restaurante.model.Credenciais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Credenciais, Long> {

    Optional<Credenciais> findByLogin(String login);

    Optional<Credenciais> findById(Long id);
}