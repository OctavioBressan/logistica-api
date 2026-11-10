package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token de acceso devuelto al autenticarse o registrarse")
public class AutenticacionResponseDTO {

    @Schema(description = "Token JWT que debe enviarse como Bearer en solicitudes protegidas", accessMode = Schema.AccessMode.READ_ONLY)
    private String token;

    public AutenticacionResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}