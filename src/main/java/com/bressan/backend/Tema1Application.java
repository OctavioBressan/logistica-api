
package com.bressan.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Punto de entrada de la API REST de logística.
 * Habilita Spring Boot y el soporte de caché utilizado por los servicios.
 */
@SpringBootApplication
@EnableCaching
public class Tema1Application {

	public static void main(String[] args) {
		SpringApplication.run(Tema1Application.class, args);
	}

}
