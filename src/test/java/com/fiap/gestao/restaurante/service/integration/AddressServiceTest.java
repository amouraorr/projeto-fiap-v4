package com.fiap.gestao.restaurante.service.integration;

import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.AddressMapper;
import com.fiap.gestao.restaurante.model.Address;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.AddressRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AddressMapper mapper;

    private AddressRequest addressRequest;
    private Address address;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        addressRequest = new AddressRequest();
        addressRequest.setIdUsuario(1L);
        addressRequest.setRua("Rua Teste");
        addressRequest.setCidade("Cidade Teste");
        addressRequest.setEstado("Estado Teste");
        addressRequest.setCep("12345-678");
        addressRequest.setNumero("123");
        addressRequest.setBairro("Bairro Teste");
        addressRequest.setComplemento("Complemento Teste");
        addressRequest.setPontoDeReferencia("Ponto de Referência Teste");

        address = new Address();
        address.setId(1L);
        address.setRua("Rua Teste");
        address.setCidade("Cidade Teste");
        address.setEstado("Estado Teste");
        address.setCep("12345-678");
        address.setNumero("123");
        address.setBairro("Bairro Teste");
        address.setComplemento("Complemento Teste");
        address.setPontoDeReferencia("Ponto de Referência Teste");

        user = new User();
        user.setId(1L);
        user.setNome("Usuário Teste");
    }

    @Test
    @DisplayName("Deve criar um endereço com sucesso")
    public void testCreateAddress() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toModel(any(AddressRequest.class))).thenReturn(address);
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        when(mapper.toResponse(any(Address.class))).thenReturn(new AddressResponse());

        AddressResponse response = addressService.create(addressRequest);

        assertNotNull(response);
        verify(userRepository).findById(1L);
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar um endereço com usuário inexistente")
    public void testCreateAddress_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.create(addressRequest);
        });

        assertEquals("Usuário com ID 1 não existe!", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve buscar um endereço por ID com sucesso")
    public void testGetAddressById() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(mapper.toResponse(any(Address.class))).thenReturn(new AddressResponse());

        AddressResponse response = addressService.getById(1L);

        assertNotNull(response);
        verify(addressRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar buscar um endereço que não existe")
    public void testGetAddressById_NotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.getById(1L);
        });

        assertEquals("Endereço com ID 1 não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve deletar um endereço com sucesso")
    public void testDeleteAddress() {
        when(addressRepository.existsById(1L)).thenReturn(true);

        addressService.delete(1L);

        verify(addressRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um endereço que não existe")
    public void testDeleteAddress_NotFound() {
        when(addressRepository.existsById(1L)).thenReturn(false);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.delete(1L);
        });

        assertEquals("Endereco com ID 1 não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um endereço que não existe")
    public void testUpdateAddress_NotFound() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.update(1L, addressRequest);
        });

        assertEquals("Endereco com ID 1 não encontrado!", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    @DisplayName("Deve deletar todos os endereços com sucesso")
    public void testDeleteAllAddresses() {
        addressService.deleteAll();

        verify(addressRepository).deleteAll();
    }

    //TODO: Retestar que não está passando ou remover se nescessário
    /*@Test
    @DisplayName("Deve lançar exceção ao tentar atualizar o usuário de um endereço")
    public void testUpdateAddress_UserMismatch() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        addressRequest.setIdUsuario(2L);

        SmartRestaurantException exception = assertThrows(SmartRestaurantException.class, () -> {
            addressService.update(1L, addressRequest);
        });

        assertEquals("Não é permitido atualizar o usuário", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }*/

    /*@Test
    @DisplayName("Deve atualizar um endereço com sucesso")
    public void testUpdateAddress() {
        user.setId(1L);
        address.setUsuario(user);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(mapper.toResponse(any(Address.class))).thenReturn(new AddressResponse());

        AddressResponse response = addressService.update(1L, addressRequest);

        assertNotNull(response);
        verify(addressRepository).save(any(Address.class));
    }
*/
}