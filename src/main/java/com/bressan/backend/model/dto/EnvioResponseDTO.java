package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Resumen de un envío y sus relaciones")
public class EnvioResponseDTO {
    @Schema(description = "Identificador del envío; en paquetes puede ser opcional", example = "1")
    private Long idEnvio;
    @Schema(description = "Código de seguimiento del envío", example = "SEG-2026-001")
    private String codigoSeguimiento;
    @Schema(description = "Fecha del envío en formato ISO (AAAA-MM-DD)", example = "2026-09-20")
    private LocalDate fechaEnvio;
    @Schema(description = "Estado operativo del envío", example = "PENDIENTE", allowableValues = {"PENDIENTE", "EN_CAMINO", "ENTREGADO"})
    private String estado;
    @Schema(description = "Identificador del transportista; opcional cuando el envío todavía no tiene uno asignado", example = "1")
    private Long idTransportista;
    @Schema(description = "Identificador del cliente", example = "1")
    private Long idCliente;
    @Schema(description = "Identificador de la dirección de origen", example = "1")
    private Long idDireccionOrigen;
    @Schema(description = "Identificador de la dirección de destino", example = "2")
    private Long idDireccionDestino;

    public Long getIdEnvio() {
        return idEnvio;
    }

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

    @Schema(description = "Cantidad total de paquetes vinculados al envío", example = "2")
    private Integer cantidadPaquetes;


    public Integer getCantidadPaquetes() { return cantidadPaquetes; }

    public void setCantidadPaquetes(Integer cantidadPaquetes) {this.cantidadPaquetes = cantidadPaquetes;}

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
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
