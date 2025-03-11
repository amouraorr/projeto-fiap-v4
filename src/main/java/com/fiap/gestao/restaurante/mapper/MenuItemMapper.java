package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.response.MenuItemResponse;
import com.fiap.gestao.restaurante.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    MenuItemResponse toResponse(MenuItem menuItem);
}