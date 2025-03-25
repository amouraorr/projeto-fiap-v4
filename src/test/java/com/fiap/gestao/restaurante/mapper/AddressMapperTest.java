package com.fiap.gestao.restaurante.mapper;

import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.model.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressMapperTest {

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    @DisplayName("Deve mapear Address para AddressResponse")
    void shouldMapAddressToAddressResponse() {
        Address address = new Address();
        address.setId(1L);
        address.setRua("Rua Exemplo");
        address.setBairro("Bairro Exemplo");
        address.setNumero("123");
        address.setComplemento("Apto 1");
        address.setCidade("Cidade Exemplo");
        address.setEstado("EX");
        address.setCep("12345-678");

        AddressResponse addressResponse = addressMapper.toResponse(address);

        assertEquals(1L, addressResponse.getId());
        assertEquals("Rua Exemplo", addressResponse.getRua());
        assertEquals("Bairro Exemplo", addressResponse.getBairro());
        assertEquals("123", addressResponse.getNumero());
        assertEquals("Apto 1", addressResponse.getComplemento());
        assertEquals("Cidade Exemplo", addressResponse.getCidade());
        assertEquals("EX", addressResponse.getEstado());
        assertEquals("12345-678", addressResponse.getCep());
    }

    @Test
    @DisplayName("Deve mapear AddressRequest para Address")
    void shouldMapAddressRequestToAddress() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setRua("Rua Exemplo");
        addressRequest.setBairro("Bairro Exemplo");
        addressRequest.setNumero("123");
        addressRequest.setComplemento("Apto 1");
        addressRequest.setCidade("Cidade Exemplo");
        addressRequest.setEstado("EX");
        addressRequest.setCep("12345-678");

        Address address = addressMapper.toModel(addressRequest);

        assertEquals("Rua Exemplo", address.getRua());
        assertEquals("Bairro Exemplo", address.getBairro());
        assertEquals("123", address.getNumero());
        assertEquals("Apto 1", address.getComplemento());
        assertEquals("Cidade Exemplo", address.getCidade());
        assertEquals("EX", address.getEstado());
        assertEquals("12345-678", address.getCep());
    }

    @Test
    @DisplayName("Deve mapear lista de Address para lista de AddressResponse")
    void shouldMapListOfAddressToListOfAddressResponse() {
        Address address1 = new Address();
        address1.setId(1L);
        address1.setRua("Rua Exemplo 1");
        address1.setBairro("Bairro Exemplo 1");
        address1.setNumero("123");
        address1.setComplemento("Apto 1");
        address1.setCidade("Cidade Exemplo 1");
        address1.setEstado("EX");
        address1.setCep("12345-678");

        Address address2 = new Address();
        address2.setId(2L);
        address2.setRua("Rua Exemplo 2");
        address2.setBairro("Bairro Exemplo 2");
        address2.setNumero("456");
        address2.setComplemento("Apto 2");
        address2.setCidade("Cidade Exemplo 2");
        address2.setEstado("EX");
        address2.setCep("98765-432");

        List<Address> addresses = Arrays.asList(address1, address2);

        List<AddressResponse> addressResponses = addressMapper.toResponses(addresses);

        assertEquals(2, addressResponses.size());
        assertEquals(1L, addressResponses.get(0).getId());
        assertEquals("Rua Exemplo 1", addressResponses.get(0).getRua());
        assertEquals(2L, addressResponses.get(1).getId());
        assertEquals("Rua Exemplo 2", addressResponses.get(1).getRua());
    }
}