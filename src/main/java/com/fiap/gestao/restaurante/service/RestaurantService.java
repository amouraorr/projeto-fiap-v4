package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.RestaurantRequest;
import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.mapper.RestaurantMapper;
import com.fiap.gestao.restaurante.mapper.UserMapper;
import com.fiap.gestao.restaurante.model.Restaurant;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.RestaurantRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private UserMapper userMapper;

    public Restaurant createRestaurant(RestaurantRequest restaurantRequest) {
        User proprietario = userRepository.findById(restaurantRequest.getIdProprietario())
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

        Restaurant restaurant = new Restaurant();
        restaurant.setNome(restaurantRequest.getNome());
        restaurant.setEndereco(restaurantRequest.getEndereco());
        restaurant.setTipoCozinha(restaurantRequest.getTipoCozinha());
        restaurant.setHorarioFuncionamento(restaurantRequest.getHorarioFuncionamento());

        proprietario = userRepository.save(proprietario);

        restaurant.setProprietario(proprietario);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        existingRestaurant.setNome(restaurantRequest.getNome());
        existingRestaurant.setEndereco(restaurantRequest.getEndereco());
        existingRestaurant.setTipoCozinha(restaurantRequest.getTipoCozinha());
        existingRestaurant.setHorarioFuncionamento(restaurantRequest.getHorarioFuncionamento());

        return restaurantRepository.save(existingRestaurant);
    }

    public List<RestaurantResponse> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> {
                    RestaurantResponse response = restaurantMapper.toResponse(restaurant);
                    response.setProprietario(userMapper.toResponse(restaurant.getProprietario()));
                    return response;
                })
                .collect(Collectors.toList());
    }


    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }
}