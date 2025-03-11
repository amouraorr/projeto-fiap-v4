package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.RestaurantRequest;
import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.model.Restaurant;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestaurantControllerTest {
    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private UserRepository userRepository;

    private RestaurantRequest restaurantRequest;
    private Restaurant restaurant;
    private RestaurantResponse restaurantResponse;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
    public void testCreateRestaurant() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(restaurantService.createRestaurant(any(RestaurantRequest.class))).thenReturn(restaurant);

        ResponseEntity<RestaurantResponse> response = restaurantController.createRestaurant(restaurantRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Restaurant", response.getBody().getNome());
        verify(userRepository).findById(1L);
        verify(restaurantService).createRestaurant(restaurantRequest);
    }

    @Test
    @DisplayName("Deve retornar erro ao criar restaurante se o proprietário não for encontrado")
    public void testCreateRestaurant_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            restaurantController.createRestaurant(restaurantRequest);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes com sucesso")
    public void testGetAllRestaurants() {
        when(restaurantService.findAll()).thenReturn(Arrays.asList(restaurantResponse));

        ResponseEntity<List<RestaurantResponse>> response = restaurantController.getAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Restaurant", response.getBody().get(0).getNome());
        verify(restaurantService).findAll();
    }

    @Test
    @DisplayName("Deve retornar restaurante por ID com sucesso")
    public void testGetRestaurantById() {
        when(restaurantService.findById(1L)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Restaurant", response.getBody().getNome());
        verify(restaurantService).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar restaurante por ID que não existe")
    public void testGetRestaurantById_NotFound() {
        when(restaurantService.findById(1L)).thenReturn(null);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(restaurantService).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar um restaurante com sucesso")
    public void testUpdateRestaurant() {
        when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.updateRestaurant(1L, restaurant);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Restaurant", response.getBody().getNome());
        verify(restaurantService).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("Deve deletar um restaurante com sucesso")
    public void testDeleteRestaurant() {
        ResponseEntity<Void> response = restaurantController.deleteRestaurant(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(restaurantService).delete(1L);
    }
}