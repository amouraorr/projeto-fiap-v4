package com.fiap.gestao.restaurante.controller;

import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.dto.response.ApiResponse;
import com.fiap.gestao.restaurante.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/address")
public class AddressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @PostMapping
    @Operation(summary = "Criação de endereço", description = "Cria um novo endereço com os dados fornecidos.")
    public ResponseEntity<AddressResponse> create(@Valid @RequestBody AddressRequest addressRequest) {
        LOGGER.info("Iniciando criação de endereço...");
        LOGGER.debug("Recebendo requisição para criar endereço: {}", addressRequest);
        var created = addressService.create(addressRequest);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca endereço por ID", description = "Retorna um endereço com base no ID fornecido.")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        LOGGER.info("Iniciando pesquisa de endereço...");
        return ResponseEntity.ok(addressService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os endereços", description = "Retorna uma lista de todos os endereços cadastrados.")
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAllUsers() {
        LOGGER.info("Iniciando pesquisa de endereços...");
        var addresses = addressService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(addresses, null));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente com base no ID fornecido.")
    public ResponseEntity<ApiResponse<AddressResponse>> update(@PathVariable Long id, @Valid @RequestBody AddressRequest addressRequest) {
        LOGGER.info("Iniciando atualizacao de endereço...");
        var updated = addressService.update(id, addressRequest);
        LOGGER.info("Endereço com ID {} atualizado com sucesso.", id);
        return ResponseEntity.ok(new ApiResponse<>(updated, null));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir endereço", description = "Exclui um endereço com base no ID fornecido.")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        LOGGER.info("Iniciando exclusao de endereço...");
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
