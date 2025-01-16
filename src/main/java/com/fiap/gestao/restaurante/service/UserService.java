package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.UserRequest;
import com.fiap.gestao.restaurante.dto.response.UserResponse;
import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.UserMapper;
import com.fiap.gestao.restaurante.repository.UserRepository;
import com.fiap.gestao.restaurante.specifications.UserSpecification;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserMapper userMapper;

    public UserResponse createUser(@Valid UserRequest userRequest) {
        LOGGER.info("Criando usuário: {}", userRequest.getNome());
        var user = userMapper.toModel(userRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserRequest updatedUserRequest) {
        var userOptional = userRepository.findById(id);
        var user = userOptional.orElseThrow(
                () -> new SmartRestaurantException(
                        String.format("Usuário com ID %d não encontrado!", id), HttpStatus.BAD_REQUEST));

        user.setNome(updatedUserRequest.getNome());
        user.setEmail(updatedUserRequest.getEmail());

        return userMapper.toResponse(userRepository.save(user));
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new SmartRestaurantException("Usuário com ID " + id + " não encontrado.", HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        LOGGER.info("Usuário com ID {} foi deletado", id);
    }

    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new SmartRestaurantException(
                                String.format("Usuário com ID %d não encontrado.", id), HttpStatus.NOT_FOUND)));

    }

    public List<UserResponse> getAllUsers() {
        return userMapper.toResponses(userRepository.findAll());
    }

    public List<UserResponse> search(String nome, String email, UserTypeEnum tipo) {
        var spec = UserSpecification.filtros(nome, email, tipo);
        return userMapper.toResponses(userRepository.findAll(spec));
    }

}
