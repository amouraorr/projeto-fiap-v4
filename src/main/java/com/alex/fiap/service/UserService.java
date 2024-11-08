package com.alex.fiap.service;

import com.alex.fiap.model.Customer;
import com.alex.fiap.model.RestaurantOwner;
import com.alex.fiap.model.User;
import com.alex.fiap.repository.UserRepository;
import com.alex.fiap.model.Endereco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {

        if (user instanceof Customer) {
            user.setTipo("customer");
        } else if (user instanceof RestaurantOwner) {
            user.setTipo("owner");

        }

        user.setNome(user.getNome());
        user.setEmail(user.getEmail());

        // Acesse os campos do endereço

        Endereco endereco = user.getEndereco();
        String rua = endereco.getRua();
        String cidade = endereco.getCidade();
        String estado = endereco.getEstado();
        String cep = endereco.getCep();

        // Defina o endereço completo
        user.setEndereco(endereco);

        // Verifique se a senha não é nula antes de codificá-la
        if (user.getSenha() != null) {
            user.setSenha(passwordEncoder.encode(user.getSenha()));
        } else {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }

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
            boolean matches = passwordEncoder.matches(senha, user.getSenha());
            LOGGER.info("Senha fornecida: {}", senha);
            LOGGER.info("Hash armazenado: {}", user.getSenha());
            LOGGER.info("Validação: {}", matches);
            return matches;
        }
        return false;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> changePassword(Long id, String newPassword) {
        return userRepository.findById(id).map(user -> {
            user.setSenha(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        });
    }

    public List<User> searchUsersByName(String nome) {
        return userRepository.findByNomeContaining(nome);
    }
}
