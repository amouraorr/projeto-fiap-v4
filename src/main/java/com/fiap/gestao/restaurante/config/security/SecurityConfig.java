package com.fiap.gestao.restaurante.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Permitir acesso a todos os endpoints sem autenticação
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().permitAll() // Permite acesso a todas as requisições
                )
                .csrf(csrf -> csrf.disable()); // Desabilita CSRF para facilitar os testes

        return http.build();
    }
}
