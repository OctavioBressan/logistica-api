package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de una dirección registrada")
public class DireccionResponseDTO {
    @Schema(description = "Identificador de la dirección", example = "1")
    private Long idDireccion;
    @Schema(description = "Nombre de la calle", example = "Belgrano")
    private String calle;
    @Schema(description = "Número de la dirección", example = "450")
    private Integer numero;
    @Schema(description = "Ciudad", example = "Río Tercero")
    private String ciudad;
    @Schema(description = "Provincia", example = "Córdoba")
    private String provincia;
    @Schema(description = "Código postal", example = "5850")
    private String codigoPostal;

    public Long getIdDireccion() {
        return idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
