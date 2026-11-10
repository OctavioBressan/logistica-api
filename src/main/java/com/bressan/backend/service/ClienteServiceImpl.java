package com.bressan.backend.service;

import com.bressan.backend.exception.RecursoNoEncontradoException;
import com.bressan.backend.model.dto.ClienteRequestDTO;
import com.bressan.backend.model.dto.ClienteResponseDTO;
import com.bressan.backend.model.entity.Cliente;
import com.bressan.backend.model.entity.Direccion;
import com.bressan.backend.repository.ClienteRepository;
import com.bressan.backend.repository.DireccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones de negocio de clientes y sus relaciones con direcciones.
 */
@Service
@Transactional
public class    ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository,DireccionRepository direccionRepository){
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    public ClienteResponseDTO guardarCliente(ClienteRequestDTO requestDTO){
        Cliente cliente = new Cliente();

        cliente.setDni(requestDTO.getDni());
        cliente.setNombre(requestDTO.getNombre());
        cliente.setApellido(requestDTO.getApellido());
        cliente.setEmail(requestDTO.getEmail());
        cliente.setTelefono(requestDTO.getTelefono());
        List<Direccion> direcciones = new ArrayList<>();

        // Se comprueba que cada dirección enviada exista antes de asociarla al cliente.
        if(requestDTO.getIdsDirecciones() != null){
            for(Long idDireccion : requestDTO.getIdsDirecciones()){
                Direccion direccion = direccionRepository.findById(idDireccion)
                        .orElseThrow(() ->
                                new RecursoNoEncontradoException("Dirección no encontrada"));

                direcciones.add(direccion);
            }
        }

        cliente.setDirecciones(direcciones);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        responseDTO.setIdCliente(clienteGuardado.getIdCliente());
        responseDTO.setDni(clienteGuardado.getDni());
        responseDTO.setNombre(clienteGuardado.getNombre());
        responseDTO.setApellido(clienteGuardado.getApellido());
        responseDTO.setEmail(clienteGuardado.getEmail());
        responseDTO.setTelefono(clienteGuardado.getTelefono());
        List<Long> idsDirecciones = new ArrayList<>();

        for(Direccion direccion : clienteGuardado.getDirecciones()){
            idsDirecciones.add(direccion.getIdDireccion());
        }

        responseDTO.setIdsDirecciones(idsDirecciones);

        return responseDTO;
    }

    @Override
    public List<ClienteResponseDTO> listarClientes(){
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(cliente -> {
                    ClienteResponseDTO responseDTO = new ClienteResponseDTO();

                    responseDTO.setIdCliente(cliente.getIdCliente());
                    responseDTO.setDni(cliente.getDni());
                    responseDTO.setNombre(cliente.getNombre());
                    responseDTO.setApellido(cliente.getApellido());
                    responseDTO.setTelefono(cliente.getTelefono());
                    responseDTO.setEmail(cliente.getEmail());
                    List<Long> idsDirecciones = new ArrayList<>();

                    for(Direccion direccion : cliente.getDirecciones()){
                        idsDirecciones.add(direccion.getIdDireccion());
                    }

                    responseDTO.setIdsDirecciones(idsDirecciones);

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public ClienteResponseDTO buscarClientePorId(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        responseDTO.setIdCliente(cliente.getIdCliente());
        responseDTO.setDni(cliente.getDni());
        responseDTO.setNombre(cliente.getNombre());
        responseDTO.setApellido(cliente.getApellido());;
        responseDTO.setEmail(cliente.getEmail());
        responseDTO.setTelefono(cliente.getTelefono());
        List<Long> idsDirecciones = new ArrayList<>();

        for(Direccion direccion : cliente.getDirecciones()){
            idsDirecciones.add(direccion.getIdDireccion());
        }

        responseDTO.setIdsDirecciones(idsDirecciones);

        return responseDTO;
    }

    @Override
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO requestDTO){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        cliente.setDni(requestDTO.getDni());
        cliente.setNombre(requestDTO.getNombre());
        cliente.setApellido(requestDTO.getApellido());
        cliente.setEmail(requestDTO.getEmail());
        cliente.setTelefono(requestDTO.getTelefono());

        List<Direccion> direcciones = new ArrayList<>();

        if(requestDTO.getIdsDirecciones() != null){
            for(Long idDireccion : requestDTO.getIdsDirecciones()){
                Direccion direccion = direccionRepository.findById(idDireccion)
                        .orElseThrow(() ->
                                new RecursoNoEncontradoException("Dirección no encontrada"));

                direcciones.add(direccion);
            }
        }

        cliente.setDirecciones(direcciones);

        Cliente clienteActualizado = clienteRepository.save( cliente);

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        responseDTO.setIdCliente(clienteActualizado.getIdCliente());
        responseDTO.setDni(clienteActualizado.getDni());
        responseDTO.setNombre(clienteActualizado.getNombre());
        responseDTO.setApellido(clienteActualizado.getApellido());
        responseDTO.setEmail(clienteActualizado.getEmail());
        responseDTO.setTelefono(clienteActualizado.getTelefono());
        List<Long> idsDirecciones = new ArrayList<>();

        for(Direccion direccion : cliente.getDirecciones()){
            idsDirecciones.add(direccion.getIdDireccion());
        }

        responseDTO.setIdsDirecciones(idsDirecciones);

        return responseDTO;
    }

    @Override
    public ClienteResponseDTO eliminarCliente(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        responseDTO.setIdCliente(cliente.getIdCliente());
        responseDTO.setDni(cliente.getDni());
        responseDTO.setNombre(cliente.getNombre());
        responseDTO.setApellido(cliente.getApellido());
        List<Long> idsDirecciones = new ArrayList<>();

        for(Direccion direccion : cliente.getDirecciones()){
            idsDirecciones.add(direccion.getIdDireccion());
        }

        responseDTO.setIdsDirecciones(idsDirecciones);
        responseDTO.setTelefono(cliente.getTelefono());
        responseDTO.setEmail(cliente.getEmail());

        clienteRepository.deleteById(id);

        return responseDTO;
    }

    @Override
    public ClienteResponseDTO buscarClientePorDni(String dni){
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        responseDTO.setIdCliente(cliente.getIdCliente());
        responseDTO.setNombre(cliente.getNombre());
        responseDTO.setApellido(cliente.getApellido());
        responseDTO.setDni(cliente.getDni());
        responseDTO.setEmail(cliente.getEmail());
        responseDTO.setTelefono(cliente.getTelefono());
        List<Long> idsDirecciones = new ArrayList<>();

        for(Direccion direccion : cliente.getDirecciones()){
            idsDirecciones.add(direccion.getIdDireccion());
        }

        responseDTO.setIdsDirecciones(idsDirecciones);

        return responseDTO;
    }
}
