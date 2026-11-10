
package com.bressan.backend.controller;

import com.bressan.backend.model.dto.AutenticacionRequestDTO;
import com.bressan.backend.model.dto.AutenticacionResponseDTO;
import com.bressan.backend.model.dto.RegistroRequestDTO;
import com.bressan.backend.service.AutenticacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * Expone las operaciones públicas de registro y autenticación.
 * Delega la validación de credenciales y la generación de JWT al servicio.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones de registro y autenticación de usuarios")
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    public AutenticacionController(AutenticacionService autenticacionService){
        this.autenticacionService = autenticacionService;
    }

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario y devuelve la respuesta de autenticación")
    @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "409", description = "El correo ya está registrado")
    @SecurityRequirements
    @PostMapping("/registro")
    public AutenticacionResponseDTO registrarUsuario(@Valid @RequestBody RegistroRequestDTO requestDTO){
        return autenticacionService.registrarUsuario(requestDTO);
    }

    @Operation(summary = "Autenticar usuario", description = "Valida las credenciales del usuario y devuelve un token JWT")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @SecurityRequirements
    @PostMapping("/autenticar")
    public AutenticacionResponseDTO autenticarUsuario(@Valid @RequestBody AutenticacionRequestDTO requestDTO){
        return autenticacionService.autenticarUsuario(requestDTO);
    }
}
