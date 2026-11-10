package com.bressan.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para crear o actualizar un transportista")
public class TransportistaRequestDTO {

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 7, max = 10, message = "Ingrese un DNI valido")
    @Schema(description = "Número de documento", example = "30111001")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 30, message = "Ingrese un nombre valido de entre 2 y 30 letras")
    @Schema(description = "Nombre", example = "Juan")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 30, message = "Ingrese un apellido valido de entre 2 y 30 letras")
    @Schema(description = "Apellido", example = "Pérez")
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "Ingrese un teléfono valido")
    @Schema(description = "Número de teléfono de contacto", example = "3571223001")
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    @Schema(description = "Correo electrónico de contacto", example = "contacto@ejemplo.com")
    private String email;

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
