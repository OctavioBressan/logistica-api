package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un transportista registrado")
public class TransportistaResponseDTO {
    @Schema(description = "Identificador del transportista; opcional cuando el envío todavía no tiene uno asignado", example = "1")
    private Long idTransportista;
    @Schema(description = "Número de documento", example = "30111001")
    private String dni;
    @Schema(description = "Nombre", example = "Juan")
    private String nombre;
    @Schema(description = "Apellido", example = "Pérez")
    private String apellido;
    @Schema(description = "Número de teléfono de contacto", example = "3571223001")
    private String telefono;
    @Schema(description = "Correo electrónico de contacto", example = "contacto@ejemplo.com")
    private String email;

    public Long getIdTransportista() {
        return idTransportista;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setIdTransportista(Long idTransportista) {
        this.idTransportista = idTransportista;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
