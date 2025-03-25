package com.fiap.gestao.restaurante.service.integration;

import com.fiap.gestao.restaurante.dto.request.UpdateUserRequest;
import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.model.Credenciais;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.LoginRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    private Long userId;

    private static int counter = 0;

    @BeforeEach
    void setUp() {
        String uniqueLogin = "john_doe_" + counter++;
        Credenciais login = new Credenciais();
        login.setLogin(uniqueLogin);
        login.setSenha("password123");
        login.setTipo(UserTypeEnum.CLIENTE);
        loginRepository.save(login);

        User user = new User();
        user.setNome("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin(login);
        user.setUserType(UserTypeEnum.CLIENTE);
        userRepository.save(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login não é encontrado")
    void shouldThrowExceptionWhenLoginNotFound() {
        UserRequest userRequest = new UserRequest();
        userRequest.setIdLogin(999L);
        userRequest.setNome("John Doe");
        userRequest.setEmail("john.doe@example.com");

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("Login não cadastrado. Login é obrigatório para cadastro de usuário, por favor crie primeiro um login", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        UpdateUserRequest updatedUserRequest = new UpdateUserRequest();
        updatedUserRequest.setNome("Jane Doe");

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.updateUser(999L, updatedUserRequest);
        });

        assertEquals(String.format("Usuário com ID %d não encontrado!", 999L), exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado para deletar")
    void shouldThrowExceptionWhenUserNotFoundForDeletion() {
        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.deleteUser(999L);
        });

        assertEquals("Usuário com ID 999 não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado ao buscar por ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.getUserById(999L);
        });

        assertEquals(String.format("Usuário com ID %d não encontrado.", 999L), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve salvar um usuário com login")
    void shouldSaveUserWithLogin() {
        Credenciais login = new Credenciais();
        login.setLogin("john_doe");
        login.setSenha("password123");
        login.setTipo(UserTypeEnum.CLIENTE);
        loginRepository.save(login);

        User user = new User();
        user.setNome("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin(login);
        user.setUserType(UserTypeEnum.CLIENTE);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
    }
}