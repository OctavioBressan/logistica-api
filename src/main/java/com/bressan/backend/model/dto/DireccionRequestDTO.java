package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para crear o actualizar una dirección")
public class DireccionRequestDTO {

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 50, message = "La calle no puede superar los 50 caracteres")
    @Schema(description = "Nombre de la calle", example = "Belgrano")
    private String calle;

    @NotNull(message = "El número es obligatorio")
    @Min(value = 1, message = "El número debe ser mayor a 0")
    @Schema(description = "Número de la dirección", example = "450")
    private Integer numero;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede superar los 50 caracteres")
    @Schema(description = "Ciudad", example = "Río Tercero")
    private String ciudad;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 50, message = "La provincia no puede superar los 50 caracteres")
    @Schema(description = "Provincia", example = "Córdoba")
    private String provincia;

    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10, message = "El código postal no puede superar los 10 caracteres")
    @Schema(description = "Código postal", example = "5850")
    private String codigoPostal;

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
