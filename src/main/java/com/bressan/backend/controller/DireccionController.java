
package com.bressan.backend.controller;

import com.bressan.backend.model.dto.DireccionRequestDTO;
import com.bressan.backend.model.dto.DireccionResponseDTO;
import com.bressan.backend.service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las operaciones HTTP para administrar direcciones.
 * Devuelve DTO y no entidades JPA directamente.
 */
@RestController
@RequestMapping("/api/direcciones")
@Tag(name = "Direcciones", description = "Operaciones para gestionar las direcciones")

public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService){
        this.direccionService = direccionService;
    }

    @Operation(summary = "Guardar dirección", description = "Registra una nueva dirección en la base de datos")
    @ApiResponse(responseCode = "200", description = "Dirección guardada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public DireccionResponseDTO guardarDireccion(@Valid @RequestBody DireccionRequestDTO requestDTO){
        return direccionService.guardarDireccion(requestDTO);
    }

    @Operation(summary = "Listar direcciones", description = "Obtiene todas las direcciones registradas")
    @ApiResponse(responseCode = "200", description = "Listado de direcciones obtenido correctamente")
    @GetMapping
    public List<DireccionResponseDTO> listarDirecciones(){
        return direccionService.listarDirecciones();
    }

    @Operation(summary = "Buscar dirección por ID", description = "Obtiene los datos de una dirección mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Dirección encontrada")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @GetMapping("/{id}")
    public DireccionResponseDTO buscarDireccionPorId(@PathVariable Long id){
        return direccionService.buscarDireccionPorId(id);
    }

    @Operation(summary = "Actualizar dirección", description = "Modifica los datos de una dirección existente")
    @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @PutMapping("/{id}")
    public DireccionResponseDTO actualizarDireccion(@PathVariable Long id, @Valid @RequestBody DireccionRequestDTO requestDTO){
        return direccionService.actualizarDireccion(id, requestDTO);
    }

    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Dirección eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @DeleteMapping("/{id}")
    public DireccionResponseDTO eliminarDireccion(@PathVariable Long id){
        return direccionService.eliminarDireccion(id);
    }
}
