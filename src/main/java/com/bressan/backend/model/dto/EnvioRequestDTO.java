package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Datos necesarios para crear o actualizar un envío")
public class EnvioRequestDTO {

    @Size(max = 30, message = "El código de seguimiento no puede superar los 30 caracteres")
    @Schema(description = "Código de seguimiento del envío", example = "SEG-2026-001")
    private String codigoSeguimiento;

    @Schema(description = "Fecha del envío en formato ISO (AAAA-MM-DD)", example = "2026-09-20")
    private LocalDate fechaEnvio;

    @NotBlank(message = "El estado del envío es obligatorio")
    @Pattern(
            regexp = "PENDIENTE|EN_CAMINO|ENTREGADO",
            message = "El estado debe ser PENDIENTE, EN_CAMINO o ENTREGADO"
    )
    @Schema(description = "Estado operativo del envío", example = "PENDIENTE", allowableValues = {"PENDIENTE", "EN_CAMINO", "ENTREGADO"})
    private String estado;

    @Positive(message = "El ID del transportista debe ser positivo")
    @Schema(description = "Identificador del transportista; opcional cuando el envío todavía no tiene uno asignado", example = "1")
    private Long idTransportista;

    @Positive(message = "El ID del cliente debe ser positivo")
    @NotNull(message = "El cliente es obligatorio")
    @Schema(description = "Identificador del cliente", example = "1")
    private Long idCliente;

    @Positive(message = "El ID de la direccion de origen debe ser positivo")
    @NotNull(message = "La dirección de origen es obligatoria")
    @Schema(description = "Identificador de la dirección de origen", example = "1")
    private Long idDireccionOrigen;

    @Positive(message = "El ID de la direccion de destino debe ser positivo")
    @NotNull(message = "La dirección de destino es obligatoria")
    @Schema(description = "Identificador de la dirección de destino", example = "2")
    private Long idDireccionDestino;

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public Long getIdTransportista() {
        return idTransportista;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public Long getIdDireccionOrigen() {
        return idDireccionOrigen;
    }

    public Long getIdDireccionDestino() {
        return idDireccionDestino;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdTransportista(Long idTransportista) {
        this.idTransportista = idTransportista;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdDireccionOrigen(Long idDireccionOrigen) {
        this.idDireccionOrigen = idDireccionOrigen;
    }

    public void setIdDireccionDestino(Long idDireccionDestino) {
        this.idDireccionDestino = idDireccionDestino;
    }
}
