package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un paquete registrado")
public class PaqueteResponseDTO {
    @Schema(description = "Identificador del paquete", example = "1")
    private Long idPaquete;
    @Schema(description = "Descripción del contenido del paquete", example = "Componentes electrónicos")
    private String descripcion;
    @Schema(description = "Peso del paquete", example = "2.5")
    private Double peso;
    @Schema(description = "Alto del paquete", example = "15.0")
    private Double alto;
    @Schema(description = "Ancho del paquete", example = "25.0")
    private Double ancho;
    @Schema(description = "Largo del paquete", example = "35.0")
    private Double largo;
    @Schema(description = "Identificador del envío; en paquetes puede ser opcional", example = "1")
    private Long idEnvio;

    public Long getIdPaquete() {
        return idPaquete;
    }

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

    public void setIdPaquete(Long idPaquete) {
        this.idPaquete = idPaquete;
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
