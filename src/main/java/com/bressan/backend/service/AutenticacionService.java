package com.bressan.backend.service;

import com.bressan.backend.model.dto.AutenticacionRequestDTO;
import com.bressan.backend.model.dto.AutenticacionResponseDTO;
import com.bressan.backend.model.dto.RegistroRequestDTO;

public interface AutenticacionService {

    AutenticacionResponseDTO registrarUsuario(RegistroRequestDTO requestDTO);

    AutenticacionResponseDTO autenticarUsuario(AutenticacionRequestDTO requestDTO);
}