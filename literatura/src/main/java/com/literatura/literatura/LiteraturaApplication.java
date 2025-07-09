package com.literatura.literatura;


import com.literatura.literatura.service.ConsoleService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	private final ConsoleService consoleService;

	// Inyecta el servicio mediante el constructor
	public LiteraturaApplication(ConsoleService consoleService) {
		this.consoleService = consoleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		consoleService.showMenu();
	}
}
