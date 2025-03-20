/*
package com.fiap.gestao.restaurante.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    public void setUp() {
        userRequest = new UserRequest();
        userRequest.setNome("John Doe");
        userRequest.setEmail("john.doe@example.com");

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setNome("John Doe");
        userResponse.setEmail("john.doe@example.com");
    }

    @Test
    @DisplayName("Deve criar um novo usuário com sucesso")
    public void testCreateUser() throws Exception {
        when(userService.createUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }
*/
/*    @Test
    @DisplayName("Deve retornar erro ao tentar criar um usuário e lançar exceção")
    public void testCreateUserThrowsException() throws Exception {
        when(userService.createUser(any(UserRequest.class)))
                .thenThrow(new SmartRestaurantException("Erro ao criar usuário", HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string((Matcher<? super String>) null));
    }*//*


*/
/*    @Test
    @DisplayName("Deve retornar um usuário ao buscar por ID")
    public void testGetUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(userResponse);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }*//*


*/
/*    @Test
    @DisplayName("Deve lançar exceção quando o usuário não é encontrado ao buscar por ID")
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(new SmartRestaurantException("Usuário não encontrado", HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado"));
    }*//*


*/
/*    @Test
    @DisplayName("Deve excluir um usuário com sucesso")
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }*//*

}
*/
