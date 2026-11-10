package com.bressan.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad persistente de transportistas y envíos asignados.
 */
@Entity
@Table(name = "transportistas")
public class Transportista {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long idTransportista;


    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 7, max = 10, message = "Ingrese un DNI valido")
    @Column(nullable = false, unique = true, length = 10)
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 30, message = "Ingrese un nombre valido de entre 2 y 30 letras")
    @Column(nullable = false, length = 30)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 30, message = "Ingrese un apellido valido de entre 2 y 30 letras")
    @Column(nullable = false, length = 30)
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "Ingrese un teléfono valido")
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato valido")
    @Size(max = 100, message = "El email no puede superar los 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @OneToMany(mappedBy = "transportista")
    private List<Envio> envios = new ArrayList<>();

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

    public List<Envio> getEnvios(){ return envios; }

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

    public void setEnvios(List<Envio> envios){this.envios = envios; }
}
