package com.alex.fiap.mapper;


import com.alex.fiap.model.Endereco;
import com.alex.fiap.model.User;
import com.alex.fiap.request.EnderecoRequest;
import com.alex.fiap.request.UserRequest;
import com.alex.fiap.response.EnderecoResponse;
import com.alex.fiap.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequest userRequest);
    UserResponse toDto(User user);
    Endereco toEntity(EnderecoRequest enderecoRequest);
    EnderecoResponse toDto(Endereco endereco);

    // Adicione este método para converter listas
    List<UserResponse> toDtoList(List<User> users);
}


