package com.learn2code.shop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication()
@OpenAPIDefinition(info = @Info(title = "Shop API", version = "1.0", description = "Shop microservice"))
public class ShopApplication {

	public static void main(String[] args) throws IOException {
		//SpringApplication.run(ShopApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ShopApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
}
