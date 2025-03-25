package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.RestaurantRequest;
import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.model.Restaurant;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@RestController
@RequestMapping("/restaurantes")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Criar novo restaurante", description = "Cria um novo restaurante com os dados fornecidos.")
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
        LOGGER.info("Iniciando criação de restaurante...");

        User proprietarioExistente = userRepository.findById(restaurantRequest.getIdProprietario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Restaurant novoRestaurante = new Restaurant();
        novoRestaurante.setNome(restaurantRequest.getNome());
        novoRestaurante.setEndereco(restaurantRequest.getEndereco());
        novoRestaurante.setTipoCozinha(restaurantRequest.getTipoCozinha());
        novoRestaurante.setHorarioFuncionamento(restaurantRequest.getHorarioFuncionamento());
        novoRestaurante.setProprietario(proprietarioExistente);

        var createdRestaurant = restaurantService.createRestaurant(restaurantRequest);

        RestaurantResponse response = new RestaurantResponse();
        response.setId(createdRestaurant.getId());
        response.setNome(createdRestaurant.getNome());
        response.setEndereco(createdRestaurant.getEndereco());
        response.setTipoCozinha(createdRestaurant.getTipoCozinha());
        response.setHorarioFuncionamento(createdRestaurant.getHorarioFuncionamento());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os restaurantes", description = "Retorna uma lista de todos os restaurantes cadastrados.")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurantResponses = restaurantService.findAll();
        return ResponseEntity.ok(restaurantResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna os detalhes de um restaurante específico pelo ID.")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        return restaurant != null ? ResponseEntity.ok(restaurant) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante", description = "Atualiza os dados de um restaurante existente pelo ID.")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id);
        Restaurant updatedRestaurant = restaurantService.save(restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar restaurante", description = "Remove um restaurante existente pelo ID.")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}