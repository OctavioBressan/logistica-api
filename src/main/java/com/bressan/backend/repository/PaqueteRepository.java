package com.bressan.backend.repository;

import com.bressan.backend.model.entity.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaqueteRepository extends JpaRepository<Paquete, Long> {
}