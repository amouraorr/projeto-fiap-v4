package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.response.MenuItemResponse;
import com.fiap.gestao.restaurante.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuItemMapperTest {

    private MenuItemMapper menuItemMapper;

    @BeforeEach
    public void setUp() {
        menuItemMapper = Mappers.getMapper(MenuItemMapper.class);
    }

    @Test
    @DisplayName("Deve mapear MenuItem para MenuItemResponse")
    public void testToResponse() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setNome("Pizza");
        menuItem.setDescricao("Deliciosa pizza de queijo");
        menuItem.setPreco(new BigDecimal("29.90"));
        menuItem.setDisponibilidadeLocal(true);
        menuItem.setCaminhoFoto("caminho/para/foto.jpg");
        menuItem.setCriadoEm(LocalDateTime.now());
        menuItem.setAtualizadoEm(LocalDateTime.now());

        MenuItemResponse response = menuItemMapper.toResponse(menuItem);

        assertEquals(menuItem.getId(), response.getId());
        assertEquals(menuItem.getNome(), response.getNome());
        assertEquals(menuItem.getDescricao(), response.getDescricao());
        assertEquals(menuItem.getPreco(), response.getPreco());
        assertEquals(menuItem.isDisponibilidadeLocal(), response.getDisponibilidadeLocal());
        assertEquals(menuItem.getCaminhoFoto(), response.getCaminhoFoto());
    }
}