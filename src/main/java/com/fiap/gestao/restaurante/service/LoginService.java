package com.fiap.gestao.restaurante.service;

import com.fiap.gestao.restaurante.dto.request.ChangePasswordRequest;
import com.fiap.gestao.restaurante.dto.request.LoginRequest;
import com.fiap.gestao.restaurante.dto.response.LoginResponse;
import com.fiap.gestao.restaurante.exception.SmartRestaurantException;
import com.fiap.gestao.restaurante.mapper.LoginMapper;
import com.fiap.gestao.restaurante.repository.LoginRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private LoginMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse create(@Valid LoginRequest request) {
        LOGGER.info("Criando login...");
        var login = mapper.toModel(request);
        login.setSenha(encodePassword(login.getSenha()));
        return mapper.toResponse(loginRepository.save(login));
    }

    public LoginResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        var loginOptional = loginRepository.findById(id);
        var login = loginOptional.orElseThrow(
                () -> new SmartRestaurantException("Login nao encontrado", HttpStatus.BAD_REQUEST)
        );
        if(!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), login.getSenha())){
            throw new SmartRestaurantException("Senha atual incorrecta", HttpStatus.BAD_REQUEST);
        }
        login.setSenha(encodePassword(changePasswordRequest.getNewPassword()));
        return mapper.toResponse(loginRepository.save(login));
    }

    private String encodePassword(String password) {
        if (password != null && !password.isBlank()) {
            return passwordEncoder.encode(password);
        } else {
            throw new SmartRestaurantException("Senha não pode ser nula", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean authenticate(String login, String password) {
        var found = loginRepository.findByLogin(login)
                .orElseThrow(() -> new SmartRestaurantException("Login não encontrado", HttpStatus.BAD_REQUEST));

        return passwordEncoder.matches(password, found.getSenha());
    }
}