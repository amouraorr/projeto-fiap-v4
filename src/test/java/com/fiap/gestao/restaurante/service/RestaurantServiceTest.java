package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.RestaurantRequest;
import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.mapper.RestaurantMapper;
import com.fiap.gestao.restaurante.mapper.UserMapper;
import com.fiap.gestao.restaurante.model.Restaurant;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.RestaurantRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestaurantServiceTest {

        @InjectMocks
        private RestaurantService restaurantService;

        @Mock
        private RestaurantRepository restaurantRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private RestaurantMapper restaurantMapper;

        @Mock
        private UserMapper userMapper;

        private RestaurantRequest restaurantRequest;
        private Restaurant restaurant;
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
        }

        @Test
        @DisplayName("Testa a criação de um restaurante com um proprietário existente.")
        public void testCreateRestaurant() {
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

            Restaurant createdRestaurant = restaurantService.createRestaurant(restaurantRequest);

            assertNotNull(createdRestaurant);
            assertEquals("Test Restaurant", createdRestaurant.getNome());
            verify(userRepository).findById(1L);
            verify(restaurantRepository).save(any(Restaurant.class));
        }

        @Test
        @DisplayName("Testa a criação de um restaurante quando o proprietário não é encontrado.")
        public void testCreateRestaurant_UserNotFound() {
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                restaurantService.createRestaurant(restaurantRequest);
            });

            assertEquals("Proprietário não encontrado", exception.getMessage());
        }

        @Test
        @DisplayName("Testa a atualização de um restaurante existente.")
        public void testUpdateRestaurant() {
            when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
            when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

            Restaurant updatedRestaurant = restaurantService.updateRestaurant(1L, restaurantRequest);

            assertNotNull(updatedRestaurant);
            assertEquals("Test Restaurant", updatedRestaurant.getNome());
            verify(restaurantRepository).findById(1L);
            verify(restaurantRepository).save(any(Restaurant.class));
        }

        @Test
        @DisplayName("Testa a atualização de um restaurante que não existe.")
        public void testUpdateRestaurant_NotFound() {
            when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                restaurantService.updateRestaurant(1L, restaurantRequest);
            });

            assertEquals("Restaurante não encontrado", exception.getMessage());
        }

        @Test
        @DisplayName("Testa a listagem de todos os restaurantes.")
        public void testFindAll() {
            when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));
            when(restaurantMapper.toResponse(any(Restaurant.class))).thenReturn(new RestaurantResponse());

            List<RestaurantResponse> restaurantResponses = restaurantService.findAll();

            assertNotNull(restaurantResponses);
            assertEquals(1, restaurantResponses.size());
            verify(restaurantRepository).findAll();
        }

        @Test
        @DisplayName("Testa a busca de um restaurante por ID.")
        public void testFindById() {
            when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

            Restaurant foundRestaurant = restaurantService.findById(1L);

            assertNotNull(foundRestaurant);
            assertEquals("Test Restaurant", foundRestaurant.getNome());
            verify(restaurantRepository).findById(1L);
        }

        @Test
        @DisplayName("Testa a busca de um restaurante que não existe.")
        public void testFindById_NotFound() {
            when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

            Restaurant foundRestaurant = restaurantService.findById(1L);

            assertNull(foundRestaurant);
            verify(restaurantRepository).findById(1L);
        }

        @Test
        @DisplayName("Testa a deleção de um restaurante.")
        public void testDelete() {
            restaurantService.delete(1L);
            verify(restaurantRepository).deleteById(1L);
        }

    @Test
    @DisplayName("Testa a persistência de um restaurante")
    public void testSaveRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant savedRestaurant = restaurantService.save(restaurant);

        assertNotNull(savedRestaurant);
        assertEquals("Test Restaurant", savedRestaurant.getNome());
        verify(restaurantRepository).save(restaurant);
    }
}