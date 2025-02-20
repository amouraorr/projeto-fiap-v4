package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("Deve mapear UserRequest para User")
    void shouldMapUserRequestToUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setNome("John Doe");
        userRequest.setEmail("john.doe@example.com");

        User user = userMapper.toModel(userRequest);

        assertEquals("John Doe", user.getNome());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Deve mapear User para UserResponse")
    void shouldMapUserToUserResponse() {
        User user = new User();
        user.setId(1L);
        user.setNome("John Doe");
        user.setEmail("john.doe@example.com");

        UserResponse userResponse = userMapper.toResponse(user);

        assertEquals(1L, userResponse.getId());
        assertEquals("John Doe", userResponse.getNome());
        assertEquals("john.doe@example.com", userResponse.getEmail());
    }

    @Test
    @DisplayName("Deve mapear lista de User para lista de UserResponse")
    void shouldMapListOfUserToListOfUserResponse() {
        User user1 = new User();
        user1.setId(1L);
        user1.setNome("John Doe");
        user1.setEmail("john.doe@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setNome("Jane Doe");
        user2.setEmail("jane.doe@example.com");

        List<User> users = Arrays.asList(user1, user2);

        List<UserResponse> userResponses = userMapper.toResponses(users);

        assertEquals(2, userResponses.size());
        assertEquals(1L, userResponses.get(0).getId());
        assertEquals("John Doe", userResponses.get(0).getNome());
        assertEquals("john.doe@example.com", userResponses.get(0).getEmail());
        assertEquals(2L, userResponses.get(1).getId());
        assertEquals("Jane Doe", userResponses.get(1).getNome());
        assertEquals("jane.doe@example.com", userResponses.get(1).getEmail());
    }
}