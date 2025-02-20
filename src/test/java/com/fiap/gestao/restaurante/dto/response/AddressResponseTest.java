package com.fiap.gestao.restaurante.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressResponseTest {

    @Test
    @DisplayName("Deve criar AddressResponse e verificar atributos")
    void shouldCreateAddressResponseAndVerifyAttributes() {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setId(1L);
        addressResponse.setRua("Rua Exemplo");
        addressResponse.setBairro("Bairro Exemplo");
        addressResponse.setNumero("123");
        addressResponse.setComplemento("Apto 1");
        addressResponse.setPontoDeReferencia("Perto da escola");
        addressResponse.setCidade("Cidade Exemplo");
        addressResponse.setEstado("EX");
        addressResponse.setCep("12345-678");
        addressResponse.setCriadoEm(LocalDateTime.now());
        addressResponse.setAtualizadoEm(LocalDateTime.now());

        AddressResponse.User user = new AddressResponse.User();
        user.setNome("John Doe");
        user.setEmail("john.doe@example.com");
        addressResponse.setUsuario(user);

        assertEquals(1L, addressResponse.getId());
        assertEquals("Rua Exemplo", addressResponse.getRua());
        assertEquals("Bairro Exemplo", addressResponse.getBairro());
        assertEquals("123", addressResponse.getNumero());
        assertEquals("Apto 1", addressResponse.getComplemento());
        assertEquals("Perto da escola", addressResponse.getPontoDeReferencia());
        assertEquals("Cidade Exemplo", addressResponse.getCidade());
        assertEquals("EX", addressResponse.getEstado());
        assertEquals("12345-678", addressResponse.getCep());
        assertNotNull(addressResponse.getCriadoEm());
        assertNotNull(addressResponse.getAtualizadoEm());
        assertNotNull(addressResponse.getUsuario());
        assertEquals("John Doe", addressResponse.getUsuario().getNome());
        assertEquals("john.doe@example.com", addressResponse.getUsuario().getEmail());
    }

    @Test
    @DisplayName("Deve criar User e verificar atributos")
    void shouldCreateUserAndVerifyAttributes() {
        AddressResponse.User user = new AddressResponse.User();
        user.setNome("Jane Doe");
        user.setEmail("jane.doe@example.com");

        assertEquals("Jane Doe", user.getNome());
        assertEquals("jane.doe@example.com", user.getEmail());
    }
}