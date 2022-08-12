package com.learn2code.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class ShopApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ShopApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ShopApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
}
