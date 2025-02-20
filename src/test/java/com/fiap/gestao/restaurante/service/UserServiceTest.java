package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.UpdateUserRequest;
import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.UserMapper;
import com.fiap.gestao.restaurante.model.Login;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.LoginRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LoginRepository loginRepository;

    @MockBean
    private UserMapper userMapper;

    @InjectMocks
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void shouldCreateUserSuccessfully() {
        UserRequest userRequest = new UserRequest();
        userRequest.setIdLogin(1L);
        userRequest.setNome("John Doe");
        userRequest.setEmail("john.doe@example.com");

        Login login = new Login();
        login.setId(1L);

        User user = new User();
        user.setLogin(login);

        UserResponse userResponse = new UserResponse();

        when(loginRepository.findById(userRequest.getIdLogin())).thenReturn(Optional.of(login));
        when(userMapper.toModel(any(UserRequest.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        UserResponse response = userService.createUser(userRequest);

        assertNotNull(response);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o login não é encontrado")
    void shouldThrowExceptionWhenLoginNotFound() {
        UserRequest userRequest = new UserRequest();
        userRequest.setIdLogin(1L);
        userRequest.setNome("John Doe");
        userRequest.setEmail("john.doe@example.com");

        when(loginRepository.findById(userRequest.getIdLogin())).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("Login não cadastrado. Login é obrigatório para cadastro de usuário, por favor crie primeiro um login", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve atualizar o usuário com sucesso")
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;
        UpdateUserRequest updatedUserRequest = new UpdateUserRequest();
        updatedUserRequest.setNome("Jane Doe");
        updatedUserRequest.setEmail("jane.doe@example.com");

        User user = new User();
        user.setId(userId);
        user.setNome("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(any(User.class))).thenReturn(new UserResponse());

        UserResponse response = userService.updateUser(userId, updatedUserRequest);

        assertNotNull(response);
        assertEquals("Jane Doe", user.getNome());
        assertEquals("jane.doe@example.com", user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        Long userId = 1L;
        UpdateUserRequest updatedUserRequest = new UpdateUserRequest();
        updatedUserRequest.setNome("Jane Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.updateUser(userId, updatedUserRequest);
        });

        assertEquals(String.format("Usuário com ID %d não encontrado!", userId), exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado para deletar")
    void shouldThrowExceptionWhenUserNotFoundForDeletion() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("Usuário com ID 1 não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve deletar o usuário com sucesso")
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    @DisplayName("Deve retornar um usuário ao buscar por ID")
    void shouldReturnUserWhenFoundById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setNome("John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(any(User.class))).thenReturn(new UserResponse());

        UserResponse response = userService.getUserById(userId);

        assertNotNull(response);
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("Deve retornar todos os usuários")
    void shouldReturnAllUsers() {
        List<User> users = List.of(new User());
        List<UserResponse> userResponses = List.of(new UserResponse());

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponses(anyList())).thenReturn(userResponses);

        List<UserResponse> responses = userService.getAllUsers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado ao buscar por ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals(String.format("Usuário com ID %d não encontrado.", userId), exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
