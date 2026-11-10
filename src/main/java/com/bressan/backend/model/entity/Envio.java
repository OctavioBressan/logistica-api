package com.bressan.backend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad persistente central de la operación de envío.
 * Relaciona al cliente, las direcciones, un transportista opcional y sus paquetes.
 */
@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long idEnvio;

    @Size(max = 30, message = "El código de seguimiento no puede superar los 30 caracteres")
    @Column(length = 30)
    private String codigoSeguimiento;

    private LocalDate fechaEnvio;

    @NotBlank(message = "El estado del envío es obligatorio")
    @Pattern(
            regexp = "PENDIENTE|EN_CAMINO|ENTREGADO",
            message = "El estado debe ser PENDIENTE, EN_CAMINO o ENTREGADO"
    )
    @Column(nullable = false, length = 9)
    private String estado;

    // La colección se utiliza para calcular cantidadPaquetes en el DTO de listado.
    @OneToMany(mappedBy = "envio")
    private List<Paquete> paquetes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "transportista_id")
    private Transportista transportista;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "La dirección de origen es obligatoria")
    @ManyToOne
    @JoinColumn(name = "direccion_origen_id", nullable = false)
    private Direccion direccionOrigen;

    @NotNull(message = "La dirección de destino es obligatoria")
    @ManyToOne
    @JoinColumn(name = "direccion_destino_id", nullable = false)
    private Direccion direccionDestino;

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

    public Transportista getTransportista() {
        return transportista;
    }

    public Cliente getCliente() { return cliente; }

    public Direccion getDireccionOrigen() {
        return direccionOrigen;
    }

    public Direccion getDireccionDestino() {
        return direccionDestino;
    }

    public List<Paquete> getPaquetes() { return paquetes; }

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

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public void setDireccionOrigen(Direccion direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public void setDireccionDestino(Direccion direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public void setPaquetes(List<Paquete> paquetes) { this.paquetes = paquetes; }
}
