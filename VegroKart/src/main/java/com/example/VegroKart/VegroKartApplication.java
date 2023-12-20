package com.example.VegroKart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(description = "vegroKart documention"))
public class VegroKartApplication {

	public static void main(String[] args) {
		SpringApplication.run(VegroKartApplication.class, args);
	}

}
