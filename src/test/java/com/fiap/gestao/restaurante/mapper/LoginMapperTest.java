package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.model.Login;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginMapperTest {

    private final LoginMapper loginMapper = Mappers.getMapper(LoginMapper.class);

    @Test
    @DisplayName("Deve mapear LoginRequest para Login")
    void shouldMapLoginRequestToLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("user");
        loginRequest.setSenha("password");

        Login login = loginMapper.toModel(loginRequest);

        assertEquals("user", login.getLogin());
        assertEquals("password", login.getSenha());
    }

    @Test
    @DisplayName("Deve mapear Login para LoginResponse")
    void shouldMapLoginToLoginResponse() {
        Login login = new Login();
        login.setId(1L);
        login.setLogin("user");
        login.setSenha("encodedPassword");

        LoginResponse loginResponse = loginMapper.toResponse(login);

        assertEquals(1L, loginResponse.getId());
        assertEquals("user", loginResponse.getLogin());
        // Aqui você pode adicionar mais verificações se LoginResponse tiver mais atributos
    }
}