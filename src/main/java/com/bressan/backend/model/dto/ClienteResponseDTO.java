package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Información pública del cliente devuelta por la API")
public class ClienteResponseDTO {

    @Schema(description = "Identificador del cliente", example = "1")
    private Long idCliente;
    @Schema(description = "Número de documento", example = "30111001")
    private String dni;
    @Schema(description = "Nombre", example = "Juan")
    private String nombre;
    @Schema(description = "Apellido", example = "Pérez")
    private String apellido;
    @Schema(description = "Correo electrónico de contacto", example = "contacto@ejemplo.com")
    private String email;
    @Schema(description = "Número de teléfono de contacto", example = "3571223001")
    private String telefono;
    @Schema(description = "Identificadores de las direcciones asociadas al cliente", example = "[1, 2]")
    private List<Long> idsDirecciones;

    public Long getIdCliente() {
        return idCliente;
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

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Long> getIdsDirecciones() {
        return idsDirecciones;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIdsDirecciones(List<Long> idsDirecciones) {
        this.idsDirecciones = idsDirecciones;
    }
}
