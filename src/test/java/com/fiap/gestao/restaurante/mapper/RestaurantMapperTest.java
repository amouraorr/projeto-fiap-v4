package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestaurantMapperTest {

    private RestaurantMapper restaurantMapper;

    @BeforeEach
    public void setUp() {
        restaurantMapper = Mappers.getMapper(RestaurantMapper.class);
    }

    @Test
    @DisplayName("Deve mapear Restaurant para RestaurantResponse")
    public void testToResponse() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNome("Restaurante Teste");
        restaurant.setEndereco("Rua Teste, 123");
        restaurant.setTipoCozinha("Italiana");
        restaurant.setHorarioFuncionamento("12:00 - 22:00");
        restaurant.setCriadoEm(LocalDateTime.now());
        restaurant.setAtualizadoEm(LocalDateTime.now());

        RestaurantResponse response = restaurantMapper.toResponse(restaurant);

        assertEquals(restaurant.getId(), response.getId());
        assertEquals(restaurant.getNome(), response.getNome());
        assertEquals(restaurant.getEndereco(), response.getEndereco());
        assertEquals(restaurant.getTipoCozinha(), response.getTipoCozinha());
        assertEquals(restaurant.getHorarioFuncionamento(), response.getHorarioFuncionamento());
    }
}