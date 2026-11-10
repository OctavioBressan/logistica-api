package com.bressan.backend.service;

import com.bressan.backend.model.dto.DireccionRequestDTO;
import com.bressan.backend.model.dto.DireccionResponseDTO;

import java.util.List;

public interface DireccionService {

    DireccionResponseDTO guardarDireccion(DireccionRequestDTO requestDTO);

    List<DireccionResponseDTO> listarDirecciones();

    DireccionResponseDTO buscarDireccionPorId(Long id);

    DireccionResponseDTO actualizarDireccion(Long id, DireccionRequestDTO requestDTO);

    DireccionResponseDTO eliminarDireccion(Long id);
}