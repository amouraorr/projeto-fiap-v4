package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.response.RestaurantResponse;
import com.fiap.gestao.restaurante.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    RestaurantResponse toResponse(Restaurant restaurant);
}