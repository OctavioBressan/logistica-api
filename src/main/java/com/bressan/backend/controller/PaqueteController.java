
package com.bressan.backend.controller;

import com.bressan.backend.model.dto.PaqueteRequestDTO;
import com.bressan.backend.model.dto.PaqueteResponseDTO;
import com.bressan.backend.service.PaqueteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las operaciones HTTP sobre paquetes.
 * Las modificaciones que afecten a envíos se reflejan mediante invalidación de caché en el servicio.
 */
@RestController
@RequestMapping("/api/paquetes")
@Tag(name = "Paquetes", description = "Operaciones para gestionar los paquetes")

public class PaqueteController {

    private final PaqueteService paqueteService;

    public PaqueteController(PaqueteService paqueteService){
        this.paqueteService = paqueteService;
    }

    @Operation(summary = "Guardar paquete", description = "Registra un nuevo paquete en la base de datos")
    @ApiResponse(responseCode = "200", description = "Paquete guardado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public PaqueteResponseDTO guardarPaquete(@Valid @RequestBody PaqueteRequestDTO requestDTO){
        return paqueteService.guardarPaquete(requestDTO);
    }

    @Operation(summary = "Listar paquetes", description = "Obtiene todos los paquetes registrados")
    @ApiResponse(responseCode = "200", description = "Listado de paquetes obtenido correctamente")
    @GetMapping
    public List<PaqueteResponseDTO> listarPaquetes(){
        return paqueteService.listarPaquetes();
    }

    @Operation(summary = "Buscar paquete por ID", description = "Obtiene los datos de un paquete mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Paquete encontrado")
    @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @GetMapping("/{id}")
    public PaqueteResponseDTO buscarPaquetePorId(@PathVariable Long id){
        return paqueteService.buscarPaquetePorId(id);
    }

    @Operation(summary = "Actualizar paquete", description = "Modifica los datos de un paquete existente")
    @ApiResponse(responseCode = "200", description = "Paquete actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @PutMapping("/{id}")
    public PaqueteResponseDTO actualizarPaquete(@PathVariable Long id, @Valid @RequestBody PaqueteRequestDTO requestDTO){
        return paqueteService.actualizarPaquete(id, requestDTO);
    }

    @Operation(summary = "Eliminar paquete", description = "Elimina un paquete mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Paquete eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Paquete no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @DeleteMapping("/{id}")
    public PaqueteResponseDTO eliminarPaquete(@PathVariable Long id){
        return paqueteService.eliminarPaquete(id);
    }
}
