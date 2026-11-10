
package com.bressan.backend.controller;

import com.bressan.backend.model.dto.EnvioRequestDTO;
import com.bressan.backend.model.dto.EnvioResponseDTO;
import com.bressan.backend.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las operaciones HTTP sobre envíos.
 * Incluye el listado optimizado y una operación administrativa para invalidar la caché.
 */
@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envíos", description = "Operaciones para gestionar los envíos")

public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService){
        this.envioService = envioService;
    }

    @Operation(summary = "Guardar envío", description = "Registra un nuevo envío en la base de datos")
    @ApiResponse(responseCode = "200", description = "Envío guardado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public EnvioResponseDTO guardarEnvio(@Valid @RequestBody EnvioRequestDTO requestDTO){
        return envioService.guardarEnvio(requestDTO);
    }

    @Operation(summary = "Listar envíos", description = "Obtiene todos los envíos utilizando consultas optimizadas y caché")
    @ApiResponse(responseCode = "200", description = "Listado de envíos obtenido correctamente")
    @GetMapping
    public List<EnvioResponseDTO> listarEnvios(){
        return envioService.listarEnvios();
    }

    @Operation(summary = "Buscar envío por ID", description = "Obtiene los datos de un envío mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Envío encontrado")
    @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @GetMapping("/{id}")
    public EnvioResponseDTO buscarEnvioPorId(@PathVariable Long id){
        return envioService.buscarEnvioPorId(id);
    }

    @Operation(summary = "Actualizar envío", description = "Modifica los datos de un envío existente")
    @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @PutMapping("/{id}")
    public EnvioResponseDTO actualizarEnvio(@PathVariable Long id,@Valid @RequestBody EnvioRequestDTO requestDTO){
        return envioService.actualizarEnvio(id, requestDTO);
    }

    @Operation(summary = "Eliminar envío", description = "Elimina un envío existente mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Envío eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @DeleteMapping("/{id}")
    public EnvioResponseDTO eliminarEnvio(@PathVariable Long id){
        return envioService.eliminarEnvio(id);
    }

    @Operation(summary = "Listar envíos ordenados", description = "Obtiene los envíos ordenados por fecha")
    @ApiResponse(responseCode = "200", description = "Listado ordenado obtenido correctamente")
    @GetMapping("/ordenados")
    public List<EnvioResponseDTO> listarEnviosOrdenadosPorFecha(){
        return envioService.listarEnviosOrdenadosPorFecha();
    }

    /**
     * Permite invalidar manualmente el listado de envíos.
     * La autorización ADMIN se controla mediante PreAuthorize.
     */
    @Operation(summary = "Limpiar caché de envíos", description = "Invalida manualmente la caché del listado de envíos. Solo ADMIN")
    @ApiResponse(responseCode = "200", description = "Caché invalidada correctamente")
    @ApiResponse(responseCode = "403", description = "Acceso denegado")
    @ApiResponse(responseCode = "401", description = "Se requiere autenticación")
    @PreAuthorize("hasAuthority('ADMIN')")
    @CacheEvict(value = "envios", allEntries = true)
    @PostMapping("/cache/limpiar")
    public String limpiarCacheEnvios(){
        return "Caché de envíos invalidada correctamente";
    }
}
