package com.bressan.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad persistente que representa direcciones reutilizables.
 */
@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idDireccion;

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 50, message = "La calle no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String calle;

    @NotNull(message = "El número es obligatorio")
    @Min(value = 1, message = "El número debe ser mayor a 0")
    @Column(nullable = false)
    private Integer numero;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String ciudad;

    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 50, message = "La provincia no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String provincia;

    @NotBlank(message = "El código postal es obligatorio")
    @Size(max = 10, message = "El código postal no puede superar los 10 caracteres")
    @Column(nullable = false, length = 10)
    private String codigoPostal;

    @OneToMany(mappedBy = "direccionOrigen")
    private List<Envio> enviosOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "direccionDestino")
    private List<Envio> enviosDestino = new ArrayList<>();

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

    public List<Envio> getEnvioOrigen() {return enviosOrigen; }

    public List<Envio> getEnviosDestino() {return enviosDestino; }

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
