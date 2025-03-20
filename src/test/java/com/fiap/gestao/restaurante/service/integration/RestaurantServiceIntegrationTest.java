/*
package com.fiap.gestao.restaurante.service.integration;

import com.fiap.gestao.restaurante.dto.request.RestaurantRequest;
import com.fiap.gestao.restaurante.mapper.RestaurantMapper;
import com.fiap.gestao.restaurante.model.Login;
import com.fiap.gestao.restaurante.model.Restaurant;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.RestaurantRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.RestaurantService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class RestaurantServiceIntegrationTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    private RestaurantRequest restaurantRequest;
    private Restaurant restaurant;
    private User user;

    //TODO: Fazer

    @BeforeEach
    public void setUp() {
        restaurantRequest = new RestaurantRequest();
        restaurantRequest.setIdProprietario(1L);
        restaurantRequest.setNome("Restaurante Teste");
        restaurantRequest.setEndereco("Endereço Teste");
        restaurantRequest.setTipoCozinha("Italiana");
        restaurantRequest.setHorarioFuncionamento("10:00 - 22:00");

        Login login = new Login();
        login.setId(1L);
        login.setSenha("proprietario_teste");
        login.setLogin("senha123");

        user = new User();
        user.setId(1L);
        user.setNome("Proprietário Teste");
        user.setLogin(login);
        userRepository.save(user);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNome("Restaurante Teste");
        restaurant.setEndereco("Endereço Teste");
        restaurant.setTipoCozinha("Italiana");
        restaurant.setHorarioFuncionamento("10:00 - 22:00");
    }

    //TODO: Fazer

    @Test
    @DisplayName("Deve criar um restaurante com sucesso")
    public void testCreateRestaurant() {}

        @Test
        @DisplayName("Deve lançar exceção ao tentar criar um restaurante com proprietário inexistente")
        public void testCreateRestaurant_UserNotFound() {}

        @Test
        @DisplayName("Deve atualizar um restaurante com sucesso")
        public void testUpdateRestaurant() {}

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar um restaurante que não existe")
        public void testUpdateRestaurant_NotFound() {}

        @Test
        @DisplayName("Deve buscar todos os restaurantes com sucesso")
        public void testFindAllRestaurants() {}

        @Test
        @DisplayName("Deve buscar um restaurante por ID com sucesso")
        public void testFindRestaurantById() {}

        @Test
        @DisplayName("Deve lançar exceção ao tentar buscar um restaurante que não existe")
        public void testFindRestaurantById_NotFound() {}

        @Test
        @DisplayName("Deve deletar um restaurante com sucesso")
        public void testDeleteRestaurant() {}

        @Test
        @DisplayName("Deve lançar exceção ao tentar deletar um restaurante que não existe")
        public void testDeleteRestaurant_NotFound() {}
}*/
