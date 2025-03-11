package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.model.MenuItem;
import com.fiap.gestao.restaurante.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return save(menuItem);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedMenuItem) {
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem não encontrado"));

        existingMenuItem.setNome(updatedMenuItem.getNome());
        existingMenuItem.setDescricao(updatedMenuItem.getDescricao());
        existingMenuItem.setPreco(updatedMenuItem.getPreco());
        existingMenuItem.setDisponibilidadeLocal(updatedMenuItem.isDisponibilidadeLocal());
        existingMenuItem.setCaminhoFoto(updatedMenuItem.getCaminhoFoto());

        return save(existingMenuItem);
    }
}