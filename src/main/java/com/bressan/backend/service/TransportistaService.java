package com.bressan.backend.service;

import com.bressan.backend.model.dto.TransportistaRequestDTO;
import com.bressan.backend.model.dto.TransportistaResponseDTO;

import java.util.List;

public interface TransportistaService {

    TransportistaResponseDTO guardarTransportista(TransportistaRequestDTO requestDTO);

    List<TransportistaResponseDTO> listarTransportistas();

    TransportistaResponseDTO buscarTransportistaPorId(Long id);

    TransportistaResponseDTO actualizarTransportista(Long id, TransportistaRequestDTO requestDTO);

    TransportistaResponseDTO eliminarTransportista(Long id);
}