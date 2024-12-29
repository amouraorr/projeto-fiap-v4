package com.alex.fiap.controller;

import com.alex.fiap.exception.UserNotFoundException;
import com.alex.fiap.mapper.UserMapper;
import com.alex.fiap.request.ChangePasswordRequest;
import com.alex.fiap.model.User;
import com.alex.fiap.request.LoginRequest;
import com.alex.fiap.request.SearchUserRequestName;
import com.alex.fiap.request.UserRequest;
import com.alex.fiap.response.ApiResponse;
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


@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @PostMapping
    @Operation(summary = "Criação de usuário", description = "Cria um novo usuário com os dados fornecidos.")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        LOGGER.info("Iniciando criação de usuário...");
        LOGGER.info("Recebendo requisição para criar usuário: {}", userRequest);

        User user = userMapper.toEntity(userRequest); // Converter DTO para Entidade
        User createdUser = userService.createUser(userRequest);

        UserResponse userResponse = userMapper.toDto(createdUser); // Converter Entidade para DTO
        return ResponseEntity.ok(userResponse);

    }

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Valida as credenciais do usuário e retorna verdadeiro se forem válidas.")
    public ResponseEntity<ApiResponse<Boolean>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String login = loginRequest.getLogin();
            String senha = loginRequest.getSenha();
            boolean isValid = userService.validateLogin(login, senha);
            return ResponseEntity.ok(new ApiResponse<>(isValid, null));
        } catch (Exception e) {
            LOGGER.error("Error ao tentar fazer login: ", e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Error interno no servidor"));
        }
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
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsersByName(@Valid @RequestBody SearchUserRequestName searchUserRequestName) {
        LOGGER.info("Recebendo requisição de busca com dados: {}", searchUserRequestName);

        String nome = searchUserRequestName.getNome();
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
        return userService.updateUser(id, userRequest)
                .map(updatedUser -> {
                    LOGGER.info("Usuário com ID {} atualizado com sucesso.", id);
                    return ResponseEntity.ok(new ApiResponse<>(userMapper.toDto(updatedUser), null));
                })
                .orElseGet(() -> {
                    String errorMessage = String.format("Usuário com ID %d não encontrado.", id);
                    LOGGER.error(errorMessage);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(errorMessage));
                });
    }

    @PatchMapping("/{id}/change-password")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário com base no ID fornecido.")
    public ResponseEntity<ApiResponse<UserResponse>> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        String newPassword = changePasswordRequest.getNewPassword();

        return userService.changePassword(id, newPassword)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(userMapper.toDto(user), null)))
                .orElseGet(() -> {
                    String errorMessage = String.format("Usuário com ID %d não encontrado.", id);
                    LOGGER.error(errorMessage); // Log da mensagem de erro
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(errorMessage)); // Retorna a mensagem de erro
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário com base no ID fornecido.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            String errorMessage = e.getMessage();
            LOGGER.error(errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(errorMessage)); // Retorna a mensagem de erro
        }
    }
}
