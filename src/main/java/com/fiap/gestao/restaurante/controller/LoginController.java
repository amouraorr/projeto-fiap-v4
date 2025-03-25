package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.ChangePasswordRequest;
import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;


    @PostMapping
    @Operation(summary = "Criação de login", description = "Cria um novo login com os dados fornecidos.")
    public ResponseEntity<LoginResponse> create(@Valid @RequestBody LoginRequest loginRequest) {
        LOGGER.info("Iniciando criação de login...");
        var created = loginService.create(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário com base no ID fornecido.")
    public ResponseEntity<ApiResponse<LoginResponse>> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        var updated = loginService.changePassword(id, changePasswordRequest);
        return ResponseEntity.ok(new ApiResponse<>(updated, null));
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Realiza login com senha.")
    public ResponseEntity<Boolean> login(@Valid @RequestBody LoginRequest loginRequest) {
        var authenticated = loginService.authenticate(loginRequest.getLogin(), loginRequest.getSenha());
        return ResponseEntity.ok(authenticated);
    }
}
