package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.ChangePasswordRequest;
import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.LoginMapper;
import com.fiap.gestao.restaurante.model.Credenciais;
import com.fiap.gestao.restaurante.repository.LoginRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginServiceTest {

    @MockBean
    private LoginRepository loginRepository;

    @MockBean
    private LoginMapper mapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("Deve criar um login com sucesso")
    void shouldCreateLoginSuccessfully() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("user");
        loginRequest.setSenha("password");

        Credenciais login = new Credenciais();
        login.setLogin("user");
        login.setSenha("encodedPassword");

        LoginResponse loginResponse = new LoginResponse();

        when(mapper.toModel(any(LoginRequest.class))).thenReturn(login);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(loginRepository.save(any(Credenciais.class))).thenReturn(login);
        when(mapper.toResponse(any(Credenciais.class))).thenReturn(loginResponse);

        LoginResponse response = loginService.create(loginRequest);

        assertNotNull(response);
        verify(loginRepository).save(login);
    }

    @Test
    @DisplayName("Deve alterar a senha com sucesso")
    void shouldChangePasswordSuccessfully() {
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");

        Credenciais login = new Credenciais();
        login.setId(userId);
        login.setSenha("encodedOldPassword");

        when(loginRepository.findById(userId)).thenReturn(Optional.of(login));
        when(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), login.getSenha())).thenReturn(true);
        when(passwordEncoder.encode(changePasswordRequest.getNewPassword())).thenReturn("encodedNewPassword");
        when(loginRepository.save(any(Credenciais.class))).thenReturn(login);
        when(mapper.toResponse(any(Credenciais.class))).thenReturn(new LoginResponse());

        LoginResponse response = loginService.changePassword(userId, changePasswordRequest);

        assertNotNull(response);
        verify(loginRepository).save(login);
        assertEquals("encodedNewPassword", login.getSenha());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login não é encontrado")
    void shouldThrowExceptionWhenLoginNotFound() {
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("oldPassword");
        changePasswordRequest.setNewPassword("newPassword");

        when(loginRepository.findById(userId)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.changePassword(userId, changePasswordRequest);
        });

        assertEquals("Login nao encontrado", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha atual está incorreta")
    void shouldThrowExceptionWhenCurrentPasswordIsIncorrect() {
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("wrongOldPassword");
        changePasswordRequest.setNewPassword("newPassword");

        Credenciais login = new Credenciais();
        login.setId(userId);
        login.setSenha("encodedOldPassword");

        when(loginRepository.findById(userId)).thenReturn(Optional.of(login));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.changePassword(userId, changePasswordRequest);
        });

        assertEquals("Senha atual incorrecta", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for nula ou vazia")
    void shouldThrowExceptionWhenPasswordIsNullOrBlank() {
        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.encodePassword(null);
        });

        assertEquals("Senha não pode ser nula", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a senha for vazia")
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.encodePassword("");
        });

        assertEquals("Senha não pode ser nula", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve autenticar o usuário com sucesso")
    void shouldAuthenticateUserSuccessfully() {
        String login = "user";
        String password = "password";

        Credenciais foundLogin = new Credenciais();
        foundLogin.setLogin(login);
        foundLogin.setSenha("encodedPassword");

        when(loginRepository.findByLogin(login)).thenReturn(Optional.of(foundLogin));
        when(passwordEncoder.matches(password, foundLogin.getSenha())).thenReturn(true);

        boolean isAuthenticated = loginService.authenticate(login, password);

        assertTrue(isAuthenticated);
        verify(loginRepository).findByLogin(login);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login não é encontrado durante a autenticação")
    void shouldThrowExceptionWhenLoginNotFoundDuringAuthentication() {
        String login = "user";
        String password = "password";

        when(loginRepository.findByLogin(login)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.authenticate(login, password);
        });

        assertEquals("Login não encontrado", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}