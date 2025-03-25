package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.model.MenuItem;
import com.fiap.gestao.restaurante.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MenuItemServiceTest {

    @InjectMocks
    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    private MenuItem menuItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setNome("Test Menu Item");
        menuItem.setDescricao("Test Description");
        menuItem.setPreco(BigDecimal.valueOf(10.0));
        menuItem.setDisponibilidadeLocal(true);
        menuItem.setCaminhoFoto("test/path/to/photo.jpg");
    }

    @Test
    @DisplayName("Deve retornar todos os MenuItems")
    public void testFindAll() {
        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(menuItem));

        List<MenuItem> menuItems = menuItemService.findAll();

        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        assertEquals("Test Menu Item", menuItems.get(0).getNome());
        verify(menuItemRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar um MenuItem pelo ID")
    public void testFindById() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        MenuItem foundMenuItem = menuItemService.findById(1L);

        assertNotNull(foundMenuItem);
        assertEquals("Test Menu Item", foundMenuItem.getNome());
        verify(menuItemRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar null se o MenuItem não for encontrado pelo ID")
    public void testFindById_NotFound() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

        MenuItem foundMenuItem = menuItemService.findById(1L);

        assertNull(foundMenuItem);
        verify(menuItemRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve salvar um novo MenuItem")
    public void testSave() {
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem savedMenuItem = menuItemService.save(menuItem);

        assertNotNull(savedMenuItem);
        assertEquals("Test Menu Item", savedMenuItem.getNome());
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    @DisplayName("Deve deletar um MenuItem pelo ID")
    public void testDelete() {
        menuItemService.delete(1L);
        verify(menuItemRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve criar um novo MenuItem")
    public void testCreateMenuItem() {
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);

        assertNotNull(createdMenuItem);
        assertEquals("Test Menu Item", createdMenuItem.getNome());
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    @DisplayName("Deve atualizar um MenuItem existente")
    public void testUpdateMenuItem() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem updatedMenuItem = new MenuItem();
        updatedMenuItem.setNome("Updated Menu Item");
        updatedMenuItem.setDescricao("Updated Description");
        updatedMenuItem.setPreco(BigDecimal.valueOf(15.0));
        updatedMenuItem.setDisponibilidadeLocal(false);
        updatedMenuItem.setCaminhoFoto("updated/path/to/photo.jpg");

        MenuItem result = menuItemService.updateMenuItem(1L, updatedMenuItem);

        assertNotNull(result);
        assertEquals("Updated Menu Item", result.getNome());
        verify(menuItemRepository).findById(1L);
        verify(menuItemRepository).save(any(MenuItem.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar um MenuItem que não existe")
    public void testUpdateMenuItem_NotFound() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuItemService.updateMenuItem(1L, menuItem);
        });

        assertEquals("MenuItem não encontrado", exception.getMessage());
        verify(menuItemRepository).findById(1L);
    }
}