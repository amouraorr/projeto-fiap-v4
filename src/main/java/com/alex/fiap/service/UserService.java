package com.alex.fiap.service;

import com.alex.fiap.exception.UserNotFoundException;
import com.alex.fiap.model.User;
import com.alex.fiap.repository.UserRepository;
import com.alex.fiap.model.Address;
import com.alex.fiap.request.AddressRequest;
import com.alex.fiap.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
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

    public User createUser(@Valid UserRequest userRequest) {
        User user = new User();
        //setUserType(user);

        // Valida o endereço usando EnderecoRequest
        validateAddress(userRequest.getEndereco());

        // Preenche os dados do usuário
        user.setNome(userRequest.getNome());
        user.setEmail(userRequest.getEmail());
        user.setLogin(userRequest.getLogin());
        user.setSenha(userRequest.getSenha());
        user.setAddress(convertToEndereco(userRequest.getEndereco())); // Converte EnderecoRequest para Endereco
        user.setTipo(userRequest.getTipo()); // Define o tipo

        // Codifica a senha
        encodePassword(user);

        user.setData(new Date());
        LOGGER.info("Criando usuário: {}", user.getNome());

        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id).map(user -> {
            user.setNome(updatedUserRequest.getNome());
            user.setEmail(updatedUserRequest.getEmail());
            user.setLogin(updatedUserRequest.getLogin());

            // Criptografa a senha antes de atualizar, se ela for fornecida
            if (updatedUserRequest.getSenha() != null && !updatedUserRequest.getSenha().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(updatedUserRequest.getSenha());
                user.setSenha(encodedPassword);
            }

            user.setTipo(updatedUserRequest.getTipo());
            // Atualizar o endereço, se necessário
            user.setAddress(convertToEndereco(updatedUserRequest.getEndereco()));

            return userRepository.save(user);
        });
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuário com ID " + id + " não encontrado.");
        }
        userRepository.deleteById(id);
        LOGGER.info("Usuário com ID {} foi deletado", id);
    }


    public boolean validateLogin(String login, String senha) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean matches = passwordEncoder.matches(senha, user.getSenha());
            LOGGER.info("Validação de login para usuário: {}", login);
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
            LOGGER.info("Senha alterada para o usuário: {}", user.getNome());
            return userRepository.save(user);
        });
    }

    public List<User> searchUsersByName(String nome) {
        return userRepository.findByNomeContaining(nome);
    }

    private void validateAddress(AddressRequest addressRequest) {
        if (addressRequest == null) {
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }
        if (addressRequest.getRua() == null || addressRequest.getRua().isEmpty()) {
            throw new IllegalArgumentException("O nome da rua não pode ser nulo ou vazio");
        }
        if (addressRequest.getCidade() == null || addressRequest.getCidade().isEmpty()) {
            throw new IllegalArgumentException("O nome da cidade não pode ser nulo ou vazio");
        }
        if (addressRequest.getEstado() == null || addressRequest.getEstado().isEmpty()) {
            throw new IllegalArgumentException("O nome do estado não pode ser nulo ou vazio");
        }
        if (addressRequest.getCep() == null || addressRequest.getCep().isEmpty()) {
            throw new IllegalArgumentException("O cep não pode ser nulo ou vazio");
        }
    }

    private Address convertToEndereco(AddressRequest addressRequest) {
        Address address = new Address();
        address.setRua(addressRequest.getRua());
        address.setCidade(addressRequest.getCidade());
        address.setEstado(addressRequest.getEstado());
        address.setCep(addressRequest.getCep());
        return address;
    }

    private void encodePassword(User user) {
        if (user.getSenha() != null) {
            user.setSenha(passwordEncoder.encode(user.getSenha()));
        } else {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }
    }
}
