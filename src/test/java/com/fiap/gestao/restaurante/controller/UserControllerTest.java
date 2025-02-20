package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.UpdateUserRequest;
import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um novo usuário com sucesso")
    void shouldCreateUserSuccessfully() {
        UserRequest userRequest = new UserRequest();
        userRequest.setNome("John Doe");
        userRequest.setEmail("john.doe@example.com");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setNome("John Doe");
        userResponse.setEmail("john.doe@example.com");

        when(userService.createUser(any(UserRequest.class))).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.createUser(userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userResponse.getNome(), response.getBody().getNome());
        verify(userService).createUser(userRequest);
    }

    @Test
    @DisplayName("Deve retornar um usuário ao buscar por ID")
    void shouldReturnUserWhenFoundById() {
        Long userId = 1L;
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setNome("John Doe");

        when(userService.getUserById(userId)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getId());
        verify(userService).getUserById(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado ao buscar por ID")
    void shouldThrowExceptionWhenUserNotFoundById() {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenThrow(new SmartRestaurantException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userController.getUserById(userId);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve retornar usuários ao buscar por filtro")
    void shouldReturnUsersWhenFiltered() {
        String nome = "John";
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setNome("John Doe");

        List<UserResponse> users = List.of(userResponse);

        when(userService.search(nome, null)).thenReturn(users);

        ResponseEntity<ApiResponse<List<UserResponse>>> response = userController.searchFilter(nome, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        verify(userService).search(nome, null);
    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhum usuário é encontrado ao buscar por filtro")
    void shouldThrowExceptionWhenNoUsersFoundByFilter() {
        when(userService.search(anyString(), anyString())).thenReturn(Collections.emptyList());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            userController.searchFilter("John", null);
        });

        assertEquals("Nenhum usuário encontrado com o nome fornecido.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve atualizar um usuário com sucesso")
    void shouldUpdateUserSuccessfully() {
        Long userId = 1L;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setNome("John Doe Updated");

        UserResponse updatedUserResponse = new UserResponse();
        updatedUserResponse.setId(userId);
        updatedUserResponse.setNome("John Doe Updated");

        when(userService.updateUser(eq(userId), any(UpdateUserRequest.class))).thenReturn(updatedUserResponse);

        ResponseEntity<ApiResponse<UserResponse>> response = userController.updateUser(userId, updateUserRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedUserResponse.getNome(), response.getBody().getData().getNome());
        verify(userService).updateUser(userId, updateUserRequest);
    }

    @Test
    @DisplayName("Deve excluir um usuário com sucesso")
    void shouldDeleteUserSuccessfully() {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<ApiResponse<Void>> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).deleteUser(userId);
    }

}