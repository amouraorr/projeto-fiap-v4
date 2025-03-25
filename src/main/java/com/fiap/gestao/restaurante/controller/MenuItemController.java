package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.MenuItemRequest;
import com.fiap.gestao.restaurante.dto.response.MenuItemResponse;
import com.fiap.gestao.restaurante.model.MenuItem;
import com.fiap.gestao.restaurante.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    @Operation(summary = "Criar novo item do cardápio", description = "Cria um novo item no cardápio com os dados fornecidos.")
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest menuItemRequest) {

        MenuItem menuItem = new MenuItem();
        menuItem.setNome(menuItemRequest.getNome());
        menuItem.setDescricao(menuItemRequest.getDescricao());
        menuItem.setPreco(menuItemRequest.getPreco());
        menuItem.setDisponibilidadeLocal(menuItemRequest.getDisponibilidadeLocal());
        menuItem.setCaminhoFoto(menuItemRequest.getCaminhoFoto());

        MenuItem savedMenuItem = menuItemService.save(menuItem);

        MenuItemResponse response = new MenuItemResponse();
        response.setId(savedMenuItem.getId());
        response.setNome(savedMenuItem.getNome());
        response.setDescricao(savedMenuItem.getDescricao());
        response.setPreco(savedMenuItem.getPreco());
        response.setDisponibilidadeLocal(savedMenuItem.isDisponibilidadeLocal());
        response.setCaminhoFoto(savedMenuItem.getCaminhoFoto());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens do cardápio", description = "Retorna uma lista de todos os itens cadastrados no cardápio.")
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item do cardápio por ID", description = "Retorna os detalhes de um item específico do cardápio pelo ID.")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        MenuItem menuItem = menuItemService.findById(id);
        return menuItem != null ? ResponseEntity.ok(menuItem) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item do cardápio", description = "Atualiza os dados de um item existente do cardápio pelo ID.")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        menuItem.setId(id);
        MenuItem updatedMenuItem = menuItemService.save(menuItem);
        return ResponseEntity.ok(updatedMenuItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar item do cardápio", description = "Remove um item existente do cardápio pelo ID.")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}