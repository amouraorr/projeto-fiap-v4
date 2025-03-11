package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.MenuItemRequest;
import com.fiap.gestao.restaurante.dto.response.MenuItemResponse;
import com.fiap.gestao.restaurante.model.MenuItem;
import com.fiap.gestao.restaurante.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MenuItemControllerTest {
    @InjectMocks
    private MenuItemController menuItemController;

    @Mock
    private MenuItemService menuItemService;

    private MenuItemRequest menuItemRequest;
    private MenuItem menuItem;
    private MenuItemResponse menuItemResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        menuItemRequest = new MenuItemRequest();
        menuItemRequest.setNome("Test Menu Item");
        menuItemRequest.setDescricao("Test Description");
        menuItemRequest.setPreco(BigDecimal.valueOf(10.0));
        menuItemRequest.setDisponibilidadeLocal(true);
        menuItemRequest.setCaminhoFoto("test/path/to/photo.jpg");

        menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setNome("Test Menu Item");
        menuItem.setDescricao("Test Description");
        menuItem.setPreco(BigDecimal.valueOf(10.0));
        menuItem.setDisponibilidadeLocal(true);
        menuItem.setCaminhoFoto("test/path/to/photo.jpg");

        menuItemResponse = new MenuItemResponse();
        menuItemResponse.setId(1L);
        menuItemResponse.setNome("Test Menu Item");
        menuItemResponse.setDescricao("Test Description");
        menuItemResponse.setPreco(BigDecimal.valueOf(10.0));
        menuItemResponse.setDisponibilidadeLocal(true);
        menuItemResponse.setCaminhoFoto("test/path/to/photo.jpg");
    }

    @Test
    @DisplayName("Deve criar um novo item do cardápio com sucesso")
    public void testCreateMenuItem() {
        when(menuItemService.save(any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<MenuItemResponse> response = menuItemController.createMenuItem(menuItemRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Menu Item", response.getBody().getNome());
        verify(menuItemService).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve listar todos os itens do cardápio com sucesso")
    public void testGetAllMenuItems() {
        when(menuItemService.findAll()).thenReturn(Arrays.asList(menuItem));

        List<MenuItem> response = menuItemController.getAllMenuItems();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Test Menu Item", response.get(0).getNome());
        verify(menuItemService).findAll();
    }

    @Test
    @DisplayName("Deve retornar item do cardápio por ID com sucesso")
    public void testGetMenuItemById() {
        when(menuItemService.findById(1L)).thenReturn(menuItem);

        ResponseEntity<MenuItem> response = menuItemController.getMenuItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Menu Item", response.getBody().getNome());
        verify(menuItemService).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar item do cardápio por ID que não existe")
    public void testGetMenuItemById_NotFound() {
        when(menuItemService.findById(1L)).thenReturn(null);

        ResponseEntity<MenuItem> response = menuItemController.getMenuItemById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(menuItemService).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar um item do cardápio com sucesso")
    public void testUpdateMenuItem() {
        when(menuItemService.save(any(MenuItem.class))).thenReturn(menuItem);

        ResponseEntity<MenuItem> response = menuItemController.updateMenuItem(1L, menuItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Menu Item", response.getBody().getNome());
        verify(menuItemService).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve deletar um item do cardápio com sucesso")
    public void testDeleteMenuItem() {
        ResponseEntity<Void> response = menuItemController.deleteMenuItem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuItemService).delete(1L);
    }
}