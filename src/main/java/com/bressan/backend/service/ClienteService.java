package com.bressan.backend.service;

import com.bressan.backend.model.dto.ClienteRequestDTO;
import com.bressan.backend.model.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO guardarCliente(ClienteRequestDTO requestDTO);

    List<ClienteResponseDTO> listarClientes();

    ClienteResponseDTO buscarClientePorId(Long id);

    ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO requestDTO);

    ClienteResponseDTO eliminarCliente(Long id);

    ClienteResponseDTO buscarClientePorDni(String dni);
}
