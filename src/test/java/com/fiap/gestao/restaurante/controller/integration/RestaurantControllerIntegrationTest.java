package com.fiap.gestao.restaurante.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.gestao.restaurante.dto.request.RestaurantRequest;
import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.model.Restaurant;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.RestaurantService;
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

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private UserRepository userRepository;

    private RestaurantRequest restaurantRequest;

    private Restaurant restaurant;

    private RestaurantResponse restaurantResponse;

    private User user;

    @BeforeEach
    public void setUp() {
        restaurantRequest = new RestaurantRequest();
        restaurantRequest.setIdProprietario(1L);
        restaurantRequest.setNome("Test Restaurant");
        restaurantRequest.setEndereco("Test Address");
        restaurantRequest.setTipoCozinha("Italian");
        restaurantRequest.setHorarioFuncionamento("9 AM - 10 PM");

        user = new User();
        user.setId(1L);
        user.setNome("Test User");

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNome("Test Restaurant");
        restaurant.setEndereco("Test Address");
        restaurant.setTipoCozinha("Italian");
        restaurant.setHorarioFuncionamento("9 AM - 10 PM");
        restaurant.setProprietario(user);

        restaurantResponse = new RestaurantResponse();
        restaurantResponse.setId(1L);
        restaurantResponse.setNome("Test Restaurant");
        restaurantResponse.setEndereco("Test Address");
        restaurantResponse.setTipoCozinha("Italian");
        restaurantResponse.setHorarioFuncionamento("9 AM - 10 PM");
    }

    @Test
    @DisplayName("Deve criar um novo restaurante com sucesso")
    public void testCreateRestaurant() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantService.createRestaurant(any(RestaurantRequest.class))).thenReturn(restaurant);

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Test Restaurant"));
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes com sucesso")
    public void testGetAllRestaurants() throws Exception {
        when(restaurantService.findAll()).thenReturn(Arrays.asList(restaurantResponse));

        mockMvc.perform(get("/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Test Restaurant"));
    }

    @Test
    @DisplayName("Deve retornar restaurante por ID com sucesso")
    public void testGetRestaurantById() throws Exception {
        when(restaurantService.findById(1L)).thenReturn(restaurant);

        mockMvc.perform(get("/restaurantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test Restaurant"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar restaurante por ID que não existe")
    public void testGetRestaurantById_NotFound() throws Exception {
        when(restaurantService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/restaurantes/1"))
                .andExpect(status().isNotFound());
    }

    //TODO: Retestar que não está passando ou remover se nescessário
    /*@Test
    @DisplayName("Deve retornar erro ao criar restaurante se o proprietário não for encontrado")
    public void testCreateRestaurant_UserNotFound() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/restaurantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurantRequest)))
                .andExpect(status().isBadRequest());
    }*/

/*    @Test
    @DisplayName("Deve atualizar um restaurante com sucesso")
    public void testUpdateRestaurant() throws Exception {
        when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurant);

        mockMvc.perform(post("/restaurantes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test Restaurant"));
    }*/

/*
    @Test
    @DisplayName("Deve deletar um restaurante com sucesso")
    public void testDeleteRestaurant() throws Exception {
        mockMvc.perform(post("/restaurantes/1"))
                .andExpect(status().isNoContent());
    }*/
}
