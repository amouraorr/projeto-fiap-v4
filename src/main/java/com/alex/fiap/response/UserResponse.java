package com.alex.fiap.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String nome;
    private String email;
    private String tipo;
    private EnderecoResponse endereco;

}
