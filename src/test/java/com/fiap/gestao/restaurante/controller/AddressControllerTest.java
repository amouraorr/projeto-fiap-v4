package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import com.fiap.gestao.restaurante.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um novo endereço com sucesso")
    void shouldCreateAddressSuccessfully() {
        AddressRequest addressRequest = new AddressRequest();

        AddressResponse addressResponse = new AddressResponse();

        when(addressService.create(any(AddressRequest.class))).thenReturn(addressResponse);

        ResponseEntity<AddressResponse> response = addressController.create(addressRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(addressService).create(addressRequest);
    }

    @Test
    @DisplayName("Deve retornar um endereço ao buscar por ID")
    void shouldReturnAddressWhenFoundById() {
        Long addressId = 1L;
        AddressResponse addressResponse = new AddressResponse();

        when(addressService.getById(addressId)).thenReturn(addressResponse);

        ResponseEntity<AddressResponse> response = addressController.getAddressById(addressId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(addressService).getById(addressId);
    }

    @Test
    @DisplayName("Deve listar todos os endereços")
    void shouldListAllAddresses() {
        List<AddressResponse> addresses = List.of(new AddressResponse());

        when(addressService.getAll()).thenReturn(addresses);

        ResponseEntity<ApiResponse<List<AddressResponse>>> response = addressController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        verify(addressService).getAll();
    }

    @Test
    @DisplayName("Deve atualizar um endereço com sucesso")
    void shouldUpdateAddressSuccessfully() {
        Long addressId = 1L;
        AddressRequest addressRequest = new AddressRequest();

        AddressResponse updatedAddressResponse = new AddressResponse();

        when(addressService.update(eq(addressId), any(AddressRequest.class))).thenReturn(updatedAddressResponse);

        ResponseEntity<ApiResponse<AddressResponse>> response = addressController.update(addressId, addressRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(addressService).update(addressId, addressRequest);
    }

    @Test
    @DisplayName("Deve excluir um endereço com sucesso")
    void shouldDeleteAddressSuccessfully() {
        Long addressId = 1L;

        doNothing().when(addressService).delete(addressId);

        ResponseEntity<ApiResponse<Void>> response = addressController.delete(addressId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(addressService).delete(addressId);
    }
}
