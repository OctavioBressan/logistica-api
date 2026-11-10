package com.bressan.backend.repository;

import com.bressan.backend.model.entity.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportistaRepository extends JpaRepository<Transportista, Long> {
}