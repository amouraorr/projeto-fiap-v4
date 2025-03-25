package com.fiap.gestao.restaurante.repository;

import com.fiap.gestao.restaurante.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}