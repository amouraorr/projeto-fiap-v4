package com.fiap.gestao.restaurante.mapper;


import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toModel(UserRequest userRequest);
    UserResponse toResponse(User user);
    List<UserResponse> toResponses(List<User> users);
}


