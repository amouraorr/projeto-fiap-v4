package com.alex.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication // Anotação que indica que esta é uma aplicação Spring Boot
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args); // Inicia a aplicação Spring Boot
	}
}