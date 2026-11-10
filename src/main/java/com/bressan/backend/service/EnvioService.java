package com.bressan.backend.service;

import com.bressan.backend.model.dto.EnvioRequestDTO;
import com.bressan.backend.model.dto.EnvioResponseDTO;

import java.util.List;

public interface EnvioService {

    EnvioResponseDTO guardarEnvio(EnvioRequestDTO requestDTO);

    List<EnvioResponseDTO> listarEnvios();

    EnvioResponseDTO buscarEnvioPorId(Long id);

    EnvioResponseDTO actualizarEnvio(Long id, EnvioRequestDTO requestDTO);

    EnvioResponseDTO eliminarEnvio(Long id);

    List<EnvioResponseDTO> listarEnviosOrdenadosPorFecha();

    List<EnvioResponseDTO> listarEnviosPorCliente(Long idCliente);
}