package com.fiap.gestao.restaurante.repository;

import com.fiap.gestao.restaurante.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}