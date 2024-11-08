package com.alex.fiap.controller;

import com.alex.fiap.mapper.UserMapper;
import com.alex.fiap.model.User;
import com.alex.fiap.request.UserRequest;
import com.alex.fiap.response.UserResponse;
import com.alex.fiap.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired

    private UserMapper userMapper;

    @Autowired

    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Valida as credenciais do usuário e retorna verdadeiro se forem válidas.")
    public ResponseEntity<Boolean> login(@RequestBody Map<String, String> credentials) {

        String login = credentials.get("login");
        String senha = credentials.get("senha");
        boolean isValid = userService.validateLogin(login, senha);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping
    @Operation(summary = "Criação de usuário", description = "Cria um novo usuário com os dados fornecidos.")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        LOGGER.info("Iniciando criação de usuário...");
        LOGGER.info("Recebendo requisição para criar usuário: {}", userRequest);

        User user = userMapper.toEntity(userRequest); // Converter DTO para Entidade
        User createdUser = userService.createUser(user);

        UserResponse userResponse = userMapper.toDto(createdUser); // Converter Entidade para DTO
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID", description = "Retorna um cliente ou dono do restaurante com base no ID fornecido.")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/search")
    @Operation(summary = "Buscar usuários por nome", description = "Busca usuários cujo nome contém a sequência de caracteres fornecida.")
    public ResponseEntity<?> searchUsersByName(@RequestBody Map<String, String> request) {
        LOGGER.info("Recebendo requisição de busca com dados: {}", request);

        String nome = request.get("nome");
        if (nome == null || nome.isEmpty()) {
            LOGGER.error("O parâmetro 'nome' está ausente ou vazio.");
            return ResponseEntity.badRequest().body("O parâmetro 'nome' é obrigatório.");
        }

        List<User> users = userService.searchUsersByName(nome);
        LOGGER.info("Número de usuários encontrados: {}", users.size());
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado com o nome fornecido.");
        }

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente com base no ID fornecido.")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> updatedUser = userService.updateUser(id, user);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar login", description = "Valida o login e a senha do usuário.")
    public ResponseEntity<Boolean> validateLogin(@RequestParam String login, @RequestParam String senha) {
        boolean isValid = userService.validateLogin(login, senha);
        return ResponseEntity.ok(isValid);
    }

    @PatchMapping("/{id}/change-password")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário com base no ID fornecido.")
    public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        String newPassword = passwordMap.get("newPassword");
        return userService.changePassword(id, newPassword)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}


