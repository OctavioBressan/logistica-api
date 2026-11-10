package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciales necesarias para iniciar sesión")
public class AutenticacionRequestDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Schema(description = "Correo electrónico de contacto", example = "contacto@ejemplo.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña utilizada para autenticación; no se devuelve en las respuestas", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}