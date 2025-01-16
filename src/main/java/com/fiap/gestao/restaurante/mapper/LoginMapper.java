package com.fiap.gestao.restaurante.mapper;


import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.model.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    Login toModel(LoginRequest userRequest);
    LoginResponse toResponse(Login user);
}


