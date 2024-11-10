package com.alex.fiap.config.swagger;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PÓS GRADUAÇÃO - FIAP 2025")
                        .version("1.0.0")
                        .description("Desenvolver um backend completo e robusto utilizando o framework\n" +
                                "Spring Boot"));
    }
}
