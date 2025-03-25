package com.fiap.gestao.restaurante.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.gestao.restaurante.dto.request.ChangePasswordRequest;
import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.service.LoginService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginService loginService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Deve criar um novo login com sucesso")
    void shouldCreateLoginSuccessfully() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("john.doe");
        loginRequest.setSenha("password123");
        loginRequest.setTipo(UserTypeEnum.PROPRIETARIO);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(1L);
        loginResponse.setLogin("john.doe");
        loginResponse.setTipo(UserTypeEnum.PROPRIETARIO);

        when(loginService.create(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login").value("john.doe"))
                .andExpect(jsonPath("$.tipo").value("PROPRIETARIO"));
    }

    @Test
    @DisplayName("Deve alterar a senha com sucesso")
    void shouldChangePasswordSuccessfully() throws Exception {
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setNewPassword("newPassword123");
        changePasswordRequest.setCurrentPassword("currentPassword123");

        LoginResponse updatedLoginResponse = new LoginResponse();
        updatedLoginResponse.setId(userId);
        updatedLoginResponse.setLogin("john.doe");
        updatedLoginResponse.setTipo(UserTypeEnum.PROPRIETARIO);

        when(loginService.changePassword(eq(userId), any(ChangePasswordRequest.class))).thenReturn(updatedLoginResponse);

        mockMvc.perform(patch("/login/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userId))
                .andExpect(jsonPath("$.data.login").value("john.doe"))
                .andExpect(jsonPath("$.data.tipo").value("PROPRIETARIO"));
    }
}