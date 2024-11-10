package com.alex.fiap.mapper;

import com.alex.fiap.model.User;
import com.alex.fiap.request.EnderecoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    EnderecoRequest toDto(User user);

    User toEntity(EnderecoRequest userRequest);

    // Método para mapear Endereco para String
    default String map(EnderecoRequest endereco) {
        return endereco != null ? endereco.getRua() + ", " + endereco.getCidade() + ", " + endereco.getEstado() + ", " + endereco.getCep() : null;

    }

    // Método para mapear String para Endereco
    default EnderecoRequest map(String enderecoStr) {
        if (enderecoStr == null || enderecoStr.isEmpty()) {
            return null;
        }

        String[] parts = enderecoStr.split(", ");
        EnderecoRequest endereco = new EnderecoRequest();
        endereco.setRua(parts[0]);
        endereco.setCidade(parts[1]);
        endereco.setEstado(parts[2]);
        endereco.setCep(parts[3]);
        return endereco;
    }
}



