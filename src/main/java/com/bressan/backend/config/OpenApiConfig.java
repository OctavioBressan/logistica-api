
package com.bressan.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import org.springframework.context.annotation.Configuration;

/**
 * Configura los metadatos OpenAPI y el esquema Bearer JWT de Swagger.
 * Estas anotaciones documentan la autenticación; no sustituyen Spring Security.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Logística y Gestión de Envíos",
                version = "1.0",
                description = "API REST para gestionar clientes, direcciones, paquetes, transportistas y envíos"
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}
