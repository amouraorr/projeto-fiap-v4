package com.alex.fiap.service;

import com.alex.fiap.model.User;
import com.alex.fiap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {

        user.setData(new Date());
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {

        return userRepository.findById(id).map(user -> {

            user.setNome(updatedUser.getNome());
            user.setEmail(updatedUser.getEmail());
            user.setEndereco(updatedUser.getEndereco());
            user.setData(new Date());
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean validateLogin(String login, String senha) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LOGGER.info("Senha fornecida: {}", senha);

            LOGGER.info("Hash armazenado: {}", user.getSenha());
        }

        return false;

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();

    }
}