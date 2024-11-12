package com.alex.fiap.controller;

import com.alex.fiap.mapper.UserMapper;
import com.alex.fiap.model.User;
import com.alex.fiap.request.UserRequest;
import com.alex.fiap.response.ApiResponse; // Importar ApiResponse
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
    public ResponseEntity<ApiResponse<Boolean>> login(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String senha = credentials.get("senha");
        boolean isValid = userService.validateLogin(login, senha);
        return ResponseEntity.ok(new ApiResponse<>(isValid, null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID", description = "Retorna um cliente ou dono do restaurante com base no ID fornecido.")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    UserResponse userResponse = userMapper.toDto(user);
                    LOGGER.info("Usuário encontrado: {}", userResponse); // Log quando o usuário é encontrado
                    return ResponseEntity.ok(new ApiResponse<>(userResponse)); // Retorna apenas data
                })
                .orElseGet(() -> {
                    String errorMessage = String.format("Usuário com ID %d não encontrado.", id);
                    LOGGER.error(errorMessage);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(errorMessage)); // Retorna apenas error
                });
    }


    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = userMapper.toDtoList(users); // Converter lista de Entidades para lista de DTOs
        return ResponseEntity.ok(new ApiResponse<>(userResponses, null));
    }

    @PostMapping("/search")
    @Operation(summary = "Buscar usuários por nome", description = "Busca usuários cujo nome contém a sequência de caracteres fornecida.")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsersByName(@RequestBody Map<String, String> request) {
        LOGGER.info("Recebendo requisição de busca com dados: {}", request);

        String nome = request.get("nome");
        if (nome == null || nome.isEmpty()) {
            LOGGER.error("O parâmetro 'nome' está ausente ou vazio.");
            return ResponseEntity.badRequest().body(new ApiResponse<>(null, "O parâmetro 'nome' é obrigatório."));
        }

        List<User> users = userService.searchUsersByName(nome);
        LOGGER.info("Número de usuários encontrados: {}", users.size());
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(null, "Nenhum usuário encontrado com o nome fornecido."));
        }

        List<UserResponse> userResponses = userMapper.toDtoList(users); // Converter lista de Entidades para lista de DTOs
        return ResponseEntity.ok(new ApiResponse<>(userResponses, null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente com base no ID fornecido.")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest) // Passa diretamente o UserRequest
                .map(updatedUser -> ResponseEntity.ok(new ApiResponse<>(userMapper.toDto(updatedUser), null)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        LOGGER.info("Usuário com ID {} foi deletado", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/change-password")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário com base no ID fornecido.")
    public ResponseEntity<ApiResponse<UserResponse>> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwordMap) {
        String newPassword = passwordMap.get("newPassword");
        return userService.changePassword(id, newPassword)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(userMapper.toDto(user), null)))
                .orElse(ResponseEntity.notFound().build());
    }
}
