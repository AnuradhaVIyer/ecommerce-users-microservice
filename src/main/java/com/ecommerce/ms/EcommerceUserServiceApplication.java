package com.ecommerce.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.info.Info;
import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
	    title = "Ecommerce User Service API",
	    version = "1.0",
	    description = "User API for the E-commerce application"))
public class EcommerceUserServiceApplication {

	public static void main(String[] args) {
		// load .env file
		Dotenv dotenv = Dotenv.load();
		// load database password from .env file
		System.setProperty("spring.datasource.password", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

		
		SpringApplication.run(EcommerceUserServiceApplication.class, args);
	}

}
