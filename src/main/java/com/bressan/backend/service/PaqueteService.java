package com.bressan.backend.service;

import com.bressan.backend.model.dto.PaqueteRequestDTO;
import com.bressan.backend.model.dto.PaqueteResponseDTO;

import java.util.List;

public interface PaqueteService {

    PaqueteResponseDTO guardarPaquete(PaqueteRequestDTO requestDTO);

    List<PaqueteResponseDTO> listarPaquetes();

    PaqueteResponseDTO buscarPaquetePorId(Long id);

    PaqueteResponseDTO actualizarPaquete(Long id, PaqueteRequestDTO requestDTO);

    PaqueteResponseDTO eliminarPaquete(Long id);
}