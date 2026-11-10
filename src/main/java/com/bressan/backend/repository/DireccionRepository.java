package com.bressan.backend.repository;

import com.bressan.backend.model.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
}