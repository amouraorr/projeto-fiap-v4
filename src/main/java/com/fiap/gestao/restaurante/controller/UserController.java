package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.UpdateUserRequest;
import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            UserResponse createdUser = userService.createUser(userRequest);
            return ResponseEntity.ok(createdUser);
        } catch (SmartRestaurantException e) {
            return ResponseEntity.badRequest().body(null); // Retorna null ou uma resposta de erro apropriada
        }
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar usuários por tipo", description = "Retorna usuários com base no tipo fornecido.")
    public ResponseEntity<List<UserResponse>> getUsersByType(@PathVariable UserTypeEnum tipo) {
        LOGGER.info("Iniciando pesquisa de usuários por tipo: {}", tipo);

        var users = userService.getUsersByType(tipo);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID", description = "Retorna um cliente ou dono do restaurante com base no ID fornecido.")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        LOGGER.info("Iniciando pesquisa de usuário...");

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "Buscar usuários por filtro", description = "Busca usuários pro filtro de campos.")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchFilter(@RequestParam(required = false) String nome,
                                                                        @RequestParam(required = false) String email) {
        LOGGER.info("Iniciando pesquisa de usuário por filtro...");

        var users = userService.search(nome, email);

        LOGGER.info("Número de usuários encontrados: {}", users.size());
        if (users.isEmpty()) {
            throw new SmartRestaurantException("Nenhum usuário encontrado com o nome fornecido.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new ApiResponse<>(users, null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente com base no ID fornecido.")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        LOGGER.info("Iniciando atualizacao de usuário...");
        var updated = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(new ApiResponse<>(updated, null));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        LOGGER.info("Iniciando exclusao de usuário...");
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
