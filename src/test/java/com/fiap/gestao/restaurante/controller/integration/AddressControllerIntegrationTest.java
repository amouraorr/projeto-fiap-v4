package com.fiap.gestao.restaurante.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.model.Login;
import com.fiap.gestao.restaurante.model.User;
import com.fiap.gestao.restaurante.repository.LoginRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.service.AddressService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class AddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    private Long userId;

    @BeforeEach
    void setUp() {
        Login login = new Login();
        login.setLogin("usuario@example.com");
        login.setSenha("senhaSegura");
        login.setTipo(UserTypeEnum.CLIENTE);
        loginRepository.save(login);

        User user = new User();
        user.setNome("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin(login);
        user.setUserType(UserTypeEnum.CLIENTE);
        userRepository.save(user);

        userId = user.getId();
    }

    @Test
    @DisplayName("Deve criar um endereço com sucesso")
    void shouldCreateAddressSuccessfully() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setRua("123 Main St");
        addressRequest.setBairro("Bairro nobre");
        addressRequest.setNumero("500");
        addressRequest.setComplemento("apartamento 25");
        addressRequest.setPontoDeReferencia("condomínio morada nobre");
        addressRequest.setCidade("Springfield");
        addressRequest.setEstado("SP");
        addressRequest.setCep("12345678");
        addressRequest.setIdUsuario(1L);

        mockMvc.perform(post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rua").value("123 Main St"))
                .andExpect(jsonPath("$.cidade").value("Springfield"));
    }

    @Test
    @DisplayName("Deve retornar o endereço quando encontrado pelo ID")
    void shouldReturnAddressWhenFoundById() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setRua("123 Main St");
        addressRequest.setBairro("Bairro nobre");
        addressRequest.setNumero("500");
        addressRequest.setComplemento("apartamento 25");
        addressRequest.setPontoDeReferencia("condomínio morada nobre");
        addressRequest.setCidade("Springfield");
        addressRequest.setEstado("SP");
        addressRequest.setCep("12345678");
        addressRequest.setIdUsuario(userId);

        AddressResponse createdAddress = addressService.create(addressRequest);

        mockMvc.perform(get("/address/{id}", createdAddress.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdAddress.getId()))
                .andExpect(jsonPath("$.rua").value("123 Main St"))
                .andExpect(jsonPath("$.cidade").value("Springfield"));
    }

    @Test
    @DisplayName("Deve listar todos os endereços")
    void shouldListAllAddresses() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setRua("123 Main St");
        addressRequest.setBairro("Bairro nobre");
        addressRequest.setNumero("500");
        addressRequest.setComplemento("apartamento 25");
        addressRequest.setPontoDeReferencia("condomínio morada nobre");
        addressRequest.setCidade("Springfield");
        addressRequest.setEstado("SP");
        addressRequest.setCep("12345678");
        addressRequest.setIdUsuario(userId);

        addressService.create(addressRequest);

        mockMvc.perform(get("/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    //TODO: Retestar que não está passando ou remover se nescessário
    /*@Test
    @DisplayName("Deve atualizar o endereço com sucesso")
    void shouldUpdateAddressSuccessfully() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setRua("123 Main St");
        addressRequest.setBairro("Bairro nobre");
        addressRequest.setNumero("500");
        addressRequest.setComplemento("apartamento 25");
        addressRequest.setPontoDeReferencia("condomínio morada nobre");
        addressRequest.setCidade("Springfield");
        addressRequest.setEstado("SP");
        addressRequest.setCep("12345678");
        addressRequest.setIdUsuario(userId);

        AddressResponse createdAddress = addressService.create(addressRequest);

        AddressRequest updatedAddressRequest = new AddressRequest();
        updatedAddressRequest.setRua("456 Elm St");
        updatedAddressRequest.setBairro("Bairro nobre");
        updatedAddressRequest.setNumero("600");
        updatedAddressRequest.setComplemento("apartamento 30");
        updatedAddressRequest.setPontoDeReferencia("condomínio morada nobre");
        updatedAddressRequest.setCidade("Springfield");
        updatedAddressRequest.setEstado("SP");
        updatedAddressRequest.setCep("87654321");
        updatedAddressRequest.setIdUsuario(userId);

        mockMvc.perform(patch("/address/{id}", createdAddress.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAddressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rua").value("456 Elm St"))
                .andExpect(jsonPath("$.cidade").value("Springfield"));
    }
*/
    /*@Test
    @DisplayName("Deve deletar o endereço com sucesso")
    void shouldDeleteAddressSuccessfully() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setRua("123 Main St");
        addressRequest.setBairro("Bairro nobre");
        addressRequest.setNumero("500");
        addressRequest.setComplemento("apartamento 25");
        addressRequest.setPontoDeReferencia("condomínio morada nobre");
        addressRequest.setCidade("Springfield");
        addressRequest.setEstado("SP");
        addressRequest.setCep("12345678");
        addressRequest.setIdUsuario(userId);

        AddressResponse createdAddress = addressService.create(addressRequest);

        mockMvc.perform(delete("/address/{id}", createdAddress.getId()))
                .andExpect(status().isNoContent());
    }*/
}