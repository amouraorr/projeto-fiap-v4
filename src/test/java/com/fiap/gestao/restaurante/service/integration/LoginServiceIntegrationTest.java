package com.fiap.gestao.restaurante.service.integration;

import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.repository.LoginRepository;
import com.fiap.gestao.restaurante.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class LoginServiceIntegrationTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setLogin("test_user");
        loginRequest.setSenha("password123");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um login com senha nula")
    public void testCreateLogin_NullPassword() {
        loginRequest.setSenha(null);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.create(loginRequest);
        });

        assertEquals("Senha não pode ser nula", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}