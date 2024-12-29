package com.example;

import org.springframework.boot.SpringApplication;

public class TestShortURLServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ShortURLServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
