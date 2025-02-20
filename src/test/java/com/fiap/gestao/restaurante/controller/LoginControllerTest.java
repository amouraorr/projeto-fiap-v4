package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.ChangePasswordRequest;
import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um novo login com sucesso")
    void shouldCreateLoginSuccessfully() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("john.doe");
        loginRequest.setSenha("password123");
        loginRequest.setTipo(UserTypeEnum.PROPRIETARIO); // ou CLIENTE, dependendo do que você deseja testar

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(1L);
        loginResponse.setLogin("john.doe");
        loginResponse.setTipo(UserTypeEnum.PROPRIETARIO); // ou CLIENTE

        when(loginService.create(any(LoginRequest.class))).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> response = loginController.create(loginRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(loginResponse.getLogin(), response.getBody().getLogin());
        assertEquals(loginResponse.getTipo(), response.getBody().getTipo());
        verify(loginService).create(loginRequest);
    }

    @Test
    @DisplayName("Deve alterar a senha com sucesso")
    void shouldChangePasswordSuccessfully() {
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setNewPassword("newPassword123");

        LoginResponse updatedLoginResponse = new LoginResponse();
        updatedLoginResponse.setId(userId);
        updatedLoginResponse.setLogin("john.doe");
        updatedLoginResponse.setTipo(UserTypeEnum.PROPRIETARIO); // ou CLIENTE

        when(loginService.changePassword(eq(userId), any(ChangePasswordRequest.class))).thenReturn(updatedLoginResponse);

        ResponseEntity<ApiResponse<LoginResponse>> response = loginController.changePassword(userId, changePasswordRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedLoginResponse.getId(), response.getBody().getData().getId());
        assertEquals(updatedLoginResponse.getLogin(), response.getBody().getData().getLogin());
        assertEquals(updatedLoginResponse.getTipo(), response.getBody().getData().getTipo());
        verify(loginService).changePassword(userId, changePasswordRequest);
    }

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void shouldLoginSuccessfully() {
        String login = "john.doe";
        String password = "password123";

        when(loginService.authenticate(login, password)).thenReturn(true);

        ResponseEntity<Boolean> response = loginController.login(login, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(loginService).authenticate(login, password);
    }

    @Test
    @DisplayName("Deve falhar ao realizar login com senha incorreta")
    void shouldFailLoginWithIncorrectPassword() {
        String login = "john.doe";
        String password = "wrongPassword";

        when(loginService.authenticate(login, password)).thenReturn(false);

        ResponseEntity<Boolean> response = loginController.login(login, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(loginService).authenticate(login, password);
    }
}
