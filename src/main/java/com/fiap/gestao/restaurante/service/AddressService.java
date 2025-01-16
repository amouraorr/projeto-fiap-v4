package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.AddressMapper;
import com.fiap.gestao.restaurante.repository.AddressRepository;
import com.fiap.gestao.restaurante.repository.UserRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper mapper;

    public AddressResponse create(@Valid AddressRequest request) {
        LOGGER.info("Criando endereco...");
        var userOptional = userRepository.findById(request.getIdUsuario());
        var address = mapper.toModel(request);
        address.setUsuario(userOptional
                .orElseThrow(() ->
                        new SmartRestaurantException(
                                String.format("Usuário com ID %s não existe!"
                                        , request.getIdUsuario()), HttpStatus.BAD_REQUEST)));
        return mapper.toResponse(addressRepository.save(address));
    }

    public AddressResponse getById(Long id) {
        LOGGER.info("Buscando endereco...");
        return mapper.toResponse(
                addressRepository.findById(id)
                        .orElseThrow(() -> new SmartRestaurantException(
                                String.format("Endereço com ID %d não encontrado.", id), HttpStatus.NOT_FOUND)));
    }

    public List<AddressResponse> getAll() {
        LOGGER.info("Buscando enderecos...");
        return mapper.toResponses(addressRepository.findAll());
    }

    public void delete(Long id) {
        LOGGER.info("Deletando endereco...");
        if (!addressRepository.existsById(id)) {
            throw new SmartRestaurantException("Endereco com ID " + id + " não encontrado.", HttpStatus.NOT_FOUND);
        }
        addressRepository.deleteById(id);
    }

    public AddressResponse update(Long id, @Valid AddressRequest addressRequest) {
        LOGGER.info("Atualizando endereco...");
        var addressOptional = addressRepository.findById(id);
        var address = addressOptional
                .orElseThrow(() ->
                        new SmartRestaurantException(
                                String.format("Endereco com ID %d não encontrado!", id), HttpStatus.BAD_REQUEST));
        if (!address.getUsuario().getId().equals(addressRequest.getIdUsuario())) {
            throw new SmartRestaurantException("Não é permitido atualizar o usuário", HttpStatus.BAD_REQUEST);
        }
        address.setRua(addressRequest.getRua());
        address.setCidade(addressRequest.getCidade());
        address.setEstado(addressRequest.getEstado());
        address.setCep(addressRequest.getCep());
        address.setComplemento(addressRequest.getComplemento());
        address.setNumero(addressRequest.getNumero());
        address.setBairro(addressRequest.getBairro());
        address.setPontoDeReferencia(addressRequest.getPontoDeReferencia());

        return mapper.toResponse(addressRepository.save(address));
    }
}
