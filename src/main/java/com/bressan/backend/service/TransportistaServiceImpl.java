package com.bressan.backend.service;

import com.bressan.backend.exception.RecursoNoEncontradoException;
import com.bressan.backend.model.dto.TransportistaRequestDTO;
import com.bressan.backend.model.dto.TransportistaResponseDTO;
import com.bressan.backend.model.entity.Transportista;
import com.bressan.backend.repository.TransportistaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementa las operaciones de negocio sobre transportistas.
 */
@Service
@Transactional
public class TransportistaServiceImpl implements TransportistaService {

    private final TransportistaRepository transportistaRepository;

    public TransportistaServiceImpl(TransportistaRepository transportistaRepository){
        this.transportistaRepository = transportistaRepository;
    }

    @Override
    public TransportistaResponseDTO guardarTransportista(TransportistaRequestDTO requestDTO){
        Transportista transportista = new Transportista();

        transportista.setDni(requestDTO.getDni());
        transportista.setNombre(requestDTO.getNombre());
        transportista.setApellido(requestDTO.getApellido());
        transportista.setTelefono(requestDTO.getTelefono());
        transportista.setEmail(requestDTO.getEmail());

        Transportista transportistaGuardado = transportistaRepository.save(transportista);

        TransportistaResponseDTO responseDTO = new TransportistaResponseDTO();

        responseDTO.setIdTransportista(transportistaGuardado.getIdTransportista());
        responseDTO.setDni(transportistaGuardado.getDni());
        responseDTO.setNombre(transportistaGuardado.getNombre());
        responseDTO.setApellido(transportistaGuardado.getApellido());
        responseDTO.setTelefono(transportistaGuardado.getTelefono());
        responseDTO.setEmail(transportistaGuardado.getEmail());

        return responseDTO;
    }

    @Override
    public List<TransportistaResponseDTO> listarTransportistas(){
        List<Transportista> transportistas = transportistaRepository.findAll();

        return transportistas.stream()
                .map(transportista -> {
                    TransportistaResponseDTO responseDTO = new TransportistaResponseDTO();

                    responseDTO.setIdTransportista(transportista.getIdTransportista());
                    responseDTO.setDni(transportista.getDni());
                    responseDTO.setNombre(transportista.getNombre());
                    responseDTO.setApellido(transportista.getApellido());
                    responseDTO.setTelefono(transportista.getTelefono());
                    responseDTO.setEmail(transportista.getEmail());

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public TransportistaResponseDTO buscarTransportistaPorId(Long id){
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Transportista no encontrado"));

        TransportistaResponseDTO responseDTO = new TransportistaResponseDTO();

        responseDTO.setIdTransportista(transportista.getIdTransportista());
        responseDTO.setDni(transportista.getDni());
        responseDTO.setNombre(transportista.getNombre());
        responseDTO.setApellido(transportista.getApellido());
        responseDTO.setTelefono(transportista.getTelefono());
        responseDTO.setEmail(transportista.getEmail());

        return responseDTO;
    }

    @Override
    public TransportistaResponseDTO actualizarTransportista(Long id, TransportistaRequestDTO requestDTO){
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Transportista no encontrado"));

        transportista.setDni(requestDTO.getDni());
        transportista.setNombre(requestDTO.getNombre());
        transportista.setApellido(requestDTO.getApellido());
        transportista.setTelefono(requestDTO.getTelefono());
        transportista.setEmail(requestDTO.getEmail());

        Transportista transportistaActualizado = transportistaRepository.save(transportista);

        TransportistaResponseDTO responseDTO = new TransportistaResponseDTO();

        responseDTO.setIdTransportista(transportistaActualizado.getIdTransportista());
        responseDTO.setDni(transportistaActualizado.getDni());
        responseDTO.setNombre(transportistaActualizado.getNombre());
        responseDTO.setApellido(transportistaActualizado.getApellido());
        responseDTO.setTelefono(transportistaActualizado.getTelefono());
        responseDTO.setEmail(transportistaActualizado.getEmail());

        return responseDTO;
    }

    @Override
    public TransportistaResponseDTO eliminarTransportista(Long id){
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Transportista no encontrado"));

        transportistaRepository.deleteById(id);

        TransportistaResponseDTO responseDTO = new TransportistaResponseDTO();

        responseDTO.setIdTransportista(transportista.getIdTransportista());
        responseDTO.setDni(transportista.getDni());
        responseDTO.setNombre(transportista.getNombre());
        responseDTO.setApellido(transportista.getApellido());
        responseDTO.setTelefono(transportista.getTelefono());
        responseDTO.setEmail(transportista.getEmail());

        return responseDTO;
    }
}