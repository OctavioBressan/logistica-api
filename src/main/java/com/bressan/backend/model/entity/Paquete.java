package com.bressan.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entidad persistente que almacena la información de cada paquete.
 */
@Entity
@Table(name = "paquetes")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long idPaquete;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    @Column(nullable = false, length = 200)
    private String descripcion;

    @NotNull(message = "El peso es obligatorio")
    @Positive(message = "El peso debe ser mayor a 0")
    @Column(nullable = false)
    private Double peso;

    @NotNull(message = "El alto es obligatorio")
    @Positive(message = "El alto debe ser mayor a 0")
    @Column(nullable = false)
    private Double alto;

    @NotNull(message = "El ancho es obligatorio")
    @Positive(message = "El ancho debe ser mayor a 0")
    @Column(nullable = false)
    private Double ancho;


    @NotNull(message = "El largo es obligatorio")
    @Positive(message = "El largo debe ser mayor a 0")
    @Column(nullable = false)
    private Double largo;

    @ManyToOne
    @JoinColumn(name = "envio_id")
    private Envio envio;

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

    public Envio getEnvio() {
        return envio;
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

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }
}
