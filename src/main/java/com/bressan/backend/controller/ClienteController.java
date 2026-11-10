
package com.bressan.backend.controller;

import com.bressan.backend.model.dto.ClienteRequestDTO;
import com.bressan.backend.model.dto.ClienteResponseDTO;
import com.bressan.backend.model.dto.EnvioResponseDTO;
import com.bressan.backend.service.ClienteService;
import com.bressan.backend.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Expone las operaciones HTTP para administrar clientes.
 * Valida DTO de entrada y delega las reglas de negocio al servicio.
 */
@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones para gestionar los clientes")

public class ClienteController {
    private final ClienteService clienteService;
    private final EnvioService envioService;

    public ClienteController(ClienteService clienteService, EnvioService envioService){
        this.clienteService = clienteService;
        this.envioService = envioService;
    }

    @Operation(summary = "Guardar cliente", description = "Registra un nuevo cliente en la base de datos")
    @ApiResponse(responseCode = "200", description = "Cliente guardado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping
    public ClienteResponseDTO guardarCliente(@Valid @RequestBody ClienteRequestDTO requestDTO){
        return clienteService.guardarCliente(requestDTO);
    }

    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Listado de clientes obtenido correctamente")
    @GetMapping
    public List<ClienteResponseDTO> listarClientes(){
        return clienteService.listarClientes();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Obtiene los datos de un cliente mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @GetMapping("/{id}")
    public ClienteResponseDTO buscarClientePorId(@PathVariable Long id){
        return clienteService.buscarClientePorId(id);
    }

    @Operation(summary = "Actualizar cliente", description = "Modifica los datos de un cliente existente")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @PutMapping("/{id}")
    public ClienteResponseDTO actualizarCliente(@PathVariable Long id,@Valid @RequestBody ClienteRequestDTO requestDTO){
        return clienteService.actualizarCliente(id, requestDTO);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente mediante su identificador")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @DeleteMapping("/{id}")
    public ClienteResponseDTO eliminarCliente(@PathVariable Long id){
        return clienteService.eliminarCliente(id);
    }

    @Operation(summary = "Buscar cliente por DNI", description = "Busca un cliente mediante su número de documento")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @Parameter(name = "dni", in = ParameterIn.QUERY, description = "Número de documento del cliente", example = "30111001", required = true)
    @GetMapping("/buscar")
    public ClienteResponseDTO buscarClientePorDni(@RequestParam String dni){
        return clienteService.buscarClientePorDni(dni);
    }

    @Operation(summary = "Listar envíos de un cliente", description = "Obtiene todos los envíos asociados a un cliente")
    @ApiResponse(responseCode = "200", description = "Listado de envíos obtenido correctamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador del recurso", example = "1", required = true)
    @GetMapping("/{id}/envios")
    public List<EnvioResponseDTO> listarEnviosPorCliente(@PathVariable Long id){
        return envioService.listarEnviosPorCliente(id);
    }
}
