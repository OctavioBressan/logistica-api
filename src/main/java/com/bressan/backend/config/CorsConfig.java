package com.bressan.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configura el acceso CORS para el cliente web utilizado en desarrollo.
 * Solo permite como origen el frontend local indicado en allowedOrigins.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Autoriza los métodos y encabezados necesarios para el frontend local.
     * El tiempo maxAge permite reutilizar la respuesta del preflight en el navegador.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
