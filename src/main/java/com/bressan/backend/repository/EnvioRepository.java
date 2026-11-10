package com.bressan.backend.repository;

import com.bressan.backend.model.entity.Envio;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Consultas de envíos utilizadas por la capa de servicio.
 * Incluye una carga optimizada de los paquetes asociados al listado.
 */
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    List<Envio> findAllByOrderByFechaEnvioAsc();

    List<Envio> findByCliente_IdCliente(Long idCliente);

    /**
     * Lista envíos precargando su colección de paquetes.
     * El EntityGraph evita consultas adicionales al acceder a los paquetes del listado.
     */
    @EntityGraph(attributePaths = {"paquetes"})
    List<Envio> findAllBy();
}