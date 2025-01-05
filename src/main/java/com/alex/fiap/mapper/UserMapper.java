package com.alex.fiap.mapper;


import com.alex.fiap.model.Address;
import com.alex.fiap.model.User;
import com.alex.fiap.request.AddressRequest;
import com.alex.fiap.request.UserRequest;
import com.alex.fiap.response.AddressResponse;
import com.alex.fiap.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequest userRequest);
    UserResponse toDto(User user);
    Address toEntity(AddressRequest addressRequest);
    AddressResponse toDto(Address address);

    // Adicione este método para converter listas
    List<UserResponse> toDtoList(List<User> users);
}


