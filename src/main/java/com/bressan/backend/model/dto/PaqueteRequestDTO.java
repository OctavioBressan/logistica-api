package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para crear o actualizar un paquete")
public class PaqueteRequestDTO {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    @Schema(description = "Descripción del contenido del paquete", example = "Componentes electrónicos")
    private String descripcion;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    @Schema(description = "Peso del paquete", example = "2.5")
    private Double peso;

    @NotNull(message = "El alto es obligatorio")
    @Positive(message = "El alto debe ser mayor a 0")
    @Schema(description = "Alto del paquete", example = "15.0")
    private Double alto;

    @NotNull(message = "El ancho es obligatorio")
    @Positive(message = "El ancho debe ser mayor a 0")
    @Schema(description = "Ancho del paquete", example = "25.0")
    private Double ancho;

    @NotNull(message = "El largo es obligatorio")
    @Positive(message = "El largo debe ser mayor a 0")
    @Schema(description = "Largo del paquete", example = "35.0")
    private Double largo;

    @Positive(message = "El ID del envio debe ser positivo")
    @Schema(description = "Identificador del envío; en paquetes puede ser opcional", example = "1")
    private Long idEnvio;

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getAlto() {
        return alto;
    }

    public Double getAncho() {
        return ancho;
    }

    public Double getLargo() {
        return largo;
    }

    public Long getIdEnvio() {
        return idEnvio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }
}
