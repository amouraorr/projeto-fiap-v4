package com.fiap.gestao.restaurante.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserResponseTest {

    @Test
    @DisplayName("Deve criar UserResponse e verificar atributos")
    void shouldCreateUserResponseAndVerifyAttributes() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setNome("John Doe");
        userResponse.setEmail("john.doe@example.com");

        Set<UserResponse.Address> enderecos = new HashSet<>();
        UserResponse.Address address = new UserResponse.Address();
        address.setId(1L);
        address.setRua("Rua Exemplo");
        address.setBairro("Bairro Exemplo");
        address.setNumero("123");
        address.setComplemento("Apto 1");
        address.setPontoDeReferencia("Perto da escola");
        address.setCidade("Cidade Exemplo");
        address.setEstado("EX");
        address.setCep("12345-678");
        address.setCriadoEm(LocalDateTime.now());
        address.setAtualizadoEm(LocalDateTime.now());
        enderecos.add(address);

        userResponse.setEnderecos(enderecos);

        assertEquals(1L, userResponse.getId());
        assertEquals("John Doe", userResponse.getNome());
        assertEquals("john.doe@example.com", userResponse.getEmail());
        assertNotNull(userResponse.getEnderecos());
        assertEquals(1, userResponse.getEnderecos().size());
        assertEquals("Rua Exemplo", userResponse.getEnderecos().iterator().next().getRua());
    }

    @Test
    @DisplayName("Deve criar Address e verificar atributos")
    void shouldCreateAddressAndVerifyAttributes() {
        UserResponse.Address address = new UserResponse.Address();
        address.setId(1L);
        address.setRua("Rua Exemplo");
        address.setBairro("Bairro Exemplo");
        address.setNumero("123");
        address.setComplemento("Apto 1");
        address.setPontoDeReferencia("Perto da escola");
        address.setCidade("Cidade Exemplo");
        address.setEstado("EX");
        address.setCep("12345-678");
        address.setCriadoEm(LocalDateTime.now());
        address.setAtualizadoEm(LocalDateTime.now());

        assertEquals(1L, address.getId());
        assertEquals("Rua Exemplo", address.getRua());
        assertEquals("Bairro Exemplo", address.getBairro());
        assertEquals("123", address.getNumero());
        assertEquals("Apto 1", address.getComplemento());
        assertEquals("Perto da escola", address.getPontoDeReferencia());
        assertEquals("Cidade Exemplo", address.getCidade());
        assertEquals("EX", address.getEstado());
        assertEquals("12345-678", address.getCep());
    }
}