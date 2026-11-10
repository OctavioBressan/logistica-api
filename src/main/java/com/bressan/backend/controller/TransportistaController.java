
package com.bressan.backend.controller;

import com.bressan.backend.model.dto.TransportistaRequestDTO;
import com.bressan.backend.model.dto.TransportistaResponseDTO;
import com.bressan.backend.service.TransportistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las operaciones HTTP para administrar transportistas.
 * Mantiene el controlador separado de la lógica de persistencia.
 */
@RestController
@RequestMapping("/api/transportistas")
@Tag(name = "Transportistas", description = "Operaciones para gestionar los transportistas")

public class TransportistaController {

    private final TransportistaService transportistaService;

    public TransportistaController(TransportistaService transportistaService){
        this.transportistaService = transportistaService;
    }

    @Operation(summary = "Guardar transportista", description = "Registra un nuevo transportista en la base de datos")
    @ApiResponse(responseCode = "200", description = "Transportista guardado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public TransportistaResponseDTO guardarTransportista(@Valid @RequestBody TransportistaRequestDTO requestDTO){
        return transportistaService.guardarTransportista(requestDTO);
    }

    @Operation(summary = "Listar transportistas", description = "Obtiene todos los transportistas registrados")
    @ApiResponse(responseCode = "200", description = "Listado de transportistas obtenido correctamente")
    @GetMapping
    public List<TransportistaResponseDTO> listarTransportistas(){
        return transportistaService.listarTransportistas();
    }

    @Operation(summary = "Buscar transportista por ID", description = "Obtiene los datos de un transportista mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Transportista encontrado")
    @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @GetMapping("/{id}")
    public TransportistaResponseDTO buscarTransportistaPorId(@PathVariable Long id){
        return transportistaService.buscarTransportistaPorId(id);
    }

    @Operation(summary = "Actualizar transportista", description = "Modifica los datos de un transportista existente")
    @ApiResponse(responseCode = "200", description = "Transportista actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    @Valid
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @PutMapping("/{id}")
    public TransportistaResponseDTO actualizarTransportista(@PathVariable Long id, @Valid @RequestBody TransportistaRequestDTO requestDTO){
        return transportistaService.actualizarTransportista(id, requestDTO);
    }

    @Operation(summary = "Eliminar transportista", description = "Elimina un transportista mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Transportista eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @DeleteMapping("/{id}")
    public TransportistaResponseDTO eliminarTransportista(@PathVariable Long id){
        return transportistaService.eliminarTransportista(id);
    }
}
