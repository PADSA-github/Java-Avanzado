package com.padsa.vaultconnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.padsa.vaultconnection.configuration.VaultConfiguration;


@SpringBootApplication
public class VaultConnectionApplication {



	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VaultConnectionApplication.class, args);
		VaultConfiguration vaultConfiguration = context.getBean(VaultConfiguration.class);
		System.out.println("---Credenciales Aplicación---");
		System.out.println("Login: " + vaultConfiguration.getLogin());
		System.out.println("Password: " + vaultConfiguration.getPassword());
	
	}
}