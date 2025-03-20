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

    //TODO: Retestar que não está passando ou remover se nescessário

    /*    @Test
    @DisplayName("Deve criar um login com sucesso")
    public void testCreateLogin() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("encoded_password");

        LoginResponse response = loginService.create(loginRequest);

        assertNotNull(response);
        assertEquals("test_user", response.getLogin());
        verify(passwordEncoder).encode("password123");
    }*/

    /*@Test
    @DisplayName("Deve alterar a senha com sucesso")
    public void testChangePassword() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("password123");
        changePasswordRequest.setNewPassword("new_password");

        when(loginRepository.findById(1L)).thenReturn(Optional.of(login));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
        when(passwordEncoder.encode(any(String.class))).thenReturn("new_encoded_password");
        when(loginRepository.save(any(Login.class))).thenReturn(login);

        LoginResponse response = loginService.changePassword(1L, changePasswordRequest);

        assertNotNull(response);
        assertEquals("test_user", response.getLogin());
        verify(loginRepository).findById(1L);
        verify(passwordEncoder).matches("password123", login.getSenha());
        verify(passwordEncoder).encode("new_password");
        verify(loginRepository).save(any(Login.class));
    }*/

    /*@Test
    @DisplayName("Deve lançar exceção ao tentar alterar a senha com senha atual incorreta")
    public void testChangePassword_IncorrectCurrentPassword() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("wrong_password");
        changePasswordRequest.setNewPassword("new_password");

        when(loginRepository.findById(1L)).thenReturn(Optional.of(login));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.changePassword(1L, changePasswordRequest);
        });

        assertEquals("Senha atual incorrecta", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }*/

  /*  @Test
    @DisplayName("Deve autenticar um login com sucesso")
    public void testAuthenticate() {
        when(loginRepository.findByLogin("test_user")).thenReturn(Optional.of(login));
        when(passwordEncoder.matches("password123", login.getSenha())).thenReturn(true);

        boolean isAuthenticated = loginService.authenticate("test_user", "password123");

        assertTrue(isAuthenticated);
        verify(loginRepository).findByLogin("test_user");
        verify(passwordEncoder).matches("password123", login.getSenha());
    }*/

/*    @Test
    @DisplayName("Deve lançar exceção ao tentar autenticar um login que não existe")
    public void testAuthenticate_LoginNotFound() {
        when(loginRepository.findByLogin("test_user")).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.authenticate("test_user", "password123");
        });

        assertEquals("Login não encontrado", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }*/

/*    @Test
    @DisplayName("Deve lançar exceção ao tentar autenticar com senha incorreta")
    public void testAuthenticate_IncorrectPassword() {
        when(loginRepository.findByLogin("test_user")).thenReturn(Optional.of(login));
        when(passwordEncoder.matches("wrong_password", login.getSenha())).thenReturn(false);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            loginService.authenticate("test_user", "wrong_password");
        });

        assertEquals("Login não encontrado", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }*/
}