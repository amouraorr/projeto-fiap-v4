package com.fiap.gestao.restaurante.service.integration;

import com.fiap.gestao.restaurante.model.MenuItem;
import com.fiap.gestao.restaurante.repository.MenuItemRepository;
import com.fiap.gestao.restaurante.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class MenuItemServiceIntegrationTest {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private MenuItem menuItem;

    @BeforeEach
    public void setUp() {
        menuItem = new MenuItem();
        menuItem.setNome("Test Menu Item");
        menuItem.setDescricao("Test Description");
        menuItem.setPreco(BigDecimal.valueOf(10.99));
        menuItem.setDisponibilidadeLocal(true);
        menuItem.setCaminhoFoto("path/to/photo.jpg");

        menuItemRepository.save(menuItem);
    }

    @Test
    @DisplayName("Deve listar todos os itens de menu")
    public void testFindAll() {
        List<MenuItem> menuItems = menuItemService.findAll();

        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
        assertEquals(1, menuItems.size());
        assertEquals("Test Menu Item", menuItems.get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar um item de menu por ID")
    public void testFindById() {
        MenuItem foundMenuItem = menuItemService.findById(menuItem.getId());

        assertNotNull(foundMenuItem);
        assertEquals("Test Menu Item", foundMenuItem.getNome());
    }

    @Test
    @DisplayName("Deve retornar null ao buscar um item de menu que não existe")
    public void testFindById_NotFound() {
        MenuItem foundMenuItem = menuItemService.findById(999L);

        assertNull(foundMenuItem);
    }

    @Test
    @DisplayName("Deve criar um novo item de menu")
    public void testCreateMenuItem() {
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setNome("New Menu Item");
        newMenuItem.setDescricao("New Description");
        newMenuItem.setPreco(BigDecimal.valueOf(12.99));
        newMenuItem.setDisponibilidadeLocal(true);
        newMenuItem.setCaminhoFoto("path/to/new/photo.jpg");

        MenuItem createdMenuItem = menuItemService.createMenuItem(newMenuItem);

        assertNotNull(createdMenuItem);
        assertNotNull(createdMenuItem.getId());
        assertEquals("New Menu Item", createdMenuItem.getNome());
    }

    @Test
    @DisplayName("Deve atualizar um item de menu existente")
    public void testUpdateMenuItem() {
        MenuItem updatedMenuItem = new MenuItem();
        updatedMenuItem.setNome("Updated Menu Item");
        updatedMenuItem.setDescricao("Updated Description");
        updatedMenuItem.setPreco(BigDecimal.valueOf(15.99));
        updatedMenuItem.setDisponibilidadeLocal(false);
        updatedMenuItem.setCaminhoFoto("path/to/updated/photo.jpg");

        MenuItem result = menuItemService.updateMenuItem(menuItem.getId(), updatedMenuItem);

        assertNotNull(result);
        assertEquals("Updated Menu Item", result.getNome());
        assertEquals("Updated Description", result.getDescricao());
        assertEquals(BigDecimal.valueOf(15.99), result.getPreco());
        assertFalse(result.isDisponibilidadeLocal());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um item de menu que não existe")
    public void testUpdateMenuItem_NotFound() {
        MenuItem updatedMenuItem = new MenuItem();
        updatedMenuItem.setNome("Updated Menu Item");
        updatedMenuItem.setDescricao("Updated Description");
        updatedMenuItem.setPreco(BigDecimal.valueOf(15.99));
        updatedMenuItem.setDisponibilidadeLocal(false);
        updatedMenuItem.setCaminhoFoto("path/to/updated/photo.jpg");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuItemService.updateMenuItem(999L, updatedMenuItem);
        });

        assertEquals("MenuItem não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve deletar um item de menu existente")
    public void testDelete() {
        Long menuItemId = menuItem.getId();
        menuItemService.delete(menuItemId);

        assertNull(menuItemService.findById(menuItemId));
    }
}