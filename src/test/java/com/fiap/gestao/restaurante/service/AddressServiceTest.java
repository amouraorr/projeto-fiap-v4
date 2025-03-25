package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.AddressMapper;
import com.fiap.gestao.restaurante.model.Address;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.AddressRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AddressMapper mapper;

    @Test
    @DisplayName("Deve criar um endereço com sucesso")
    void shouldCreateAddressSuccessfully() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setIdUsuario(1L);

        User user = new User();
        user.setId(1L);

        Address address = new Address();
        address.setUsuario(user);

        AddressResponse addressResponse = new AddressResponse();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(mapper.toModel(any(AddressRequest.class))).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(mapper.toResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse response = addressService.create(addressRequest);

        assertNotNull(response);
        verify(addressRepository).save(address);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não existe")
    void shouldThrowExceptionWhenUserDoesNotExist() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setIdUsuario(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.create(addressRequest);
        });

        assertEquals("Usuário com ID 1 não existe!", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve retornar um endereço ao buscar por ID")
    void shouldReturnAddressWhenFoundById() {
        Address address = new Address();
        AddressResponse addressResponse = new AddressResponse();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(mapper.toResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse response = addressService.getById(1L);

        assertNotNull(response);
        verify(addressRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço não é encontrado por ID")
    void shouldThrowExceptionWhenAddressNotFoundById() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.getById(1L);
        });

        assertEquals("Endereço com ID 1 não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve retornar todos os endereços")
    void shouldReturnAllAddresses() {
        List<Address> addresses = List.of(new Address());
        List<AddressResponse> addressResponses = List.of(new AddressResponse());

        when(addressRepository.findAll()).thenReturn(addresses);
        when(mapper.toResponses(anyList())).thenReturn(addressResponses);

        List<AddressResponse> responses = addressService.getAll();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        verify(addressRepository).findAll();
    }

    @Test
    @DisplayName("Deve deletar o endereço quando ele existir")
    void shouldDeleteAddressWhenExists() {
        when(addressRepository.existsById(anyLong())).thenReturn(true);

        addressService.delete(1L);

        verify(addressRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço não existir para deletar")
    void shouldThrowExceptionWhenAddressNotFoundForDeletion() {
        when(addressRepository.existsById(anyLong())).thenReturn(false);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.delete(1L);
        });

        assertEquals("Endereco com ID 1 não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve atualizar o endereço com sucesso")
    void shouldUpdateAddressSuccessfully() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setIdUsuario(1L);

        User user = new User();
        user.setId(1L);

        Address address = new Address();
        address.setUsuario(user);

        AddressResponse addressResponse = new AddressResponse();

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(mapper.toResponse(any(Address.class))).thenReturn(addressResponse);

        AddressResponse response = addressService.update(1L, addressRequest);

        assertNotNull(response);
        verify(addressRepository).save(address);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o endereço não é encontrado para atualização")
    void shouldThrowExceptionWhenAddressNotFoundForUpdate() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setIdUsuario(1L);

        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.update(1L, addressRequest);
        });

        assertEquals("Endereco com ID 1 não encontrado!", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID do usuário não corresponde ao atualizar")
    void shouldThrowExceptionWhenUserIdDoesNotMatchOnUpdate() {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setIdUsuario(2L);

        User user = new User();
        user.setId(1L);

        Address address = new Address();
        address.setUsuario(user);

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.update(1L, addressRequest);
        });

        assertEquals("Não é permitido atualizar o usuário", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
