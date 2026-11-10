package com.bressan.backend.repository;

import com.bressan.backend.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Acceso a los datos de clientes mediante Spring Data JPA.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDni(String dni);
}