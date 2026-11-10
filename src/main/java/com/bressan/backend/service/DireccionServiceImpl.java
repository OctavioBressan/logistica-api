package com.bressan.backend.service;

import com.bressan.backend.exception.RecursoNoEncontradoException;
import com.bressan.backend.model.dto.DireccionRequestDTO;
import com.bressan.backend.model.dto.DireccionResponseDTO;
import com.bressan.backend.model.entity.Direccion;
import com.bressan.backend.repository.DireccionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementa las operaciones de negocio sobre direcciones.
 */
@Service
@Transactional
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;

    public DireccionServiceImpl(DireccionRepository direccionRepository){
        this.direccionRepository = direccionRepository;
    }

    @Override
    public DireccionResponseDTO guardarDireccion(DireccionRequestDTO requestDTO){
        Direccion direccion = new Direccion();

        direccion.setCalle(requestDTO.getCalle());
        direccion.setNumero(requestDTO.getNumero());
        direccion.setCiudad(requestDTO.getCiudad());
        direccion.setProvincia(requestDTO.getProvincia());
        direccion.setCodigoPostal(requestDTO.getCodigoPostal());

        Direccion direccionGuardada = direccionRepository.save(direccion);

        DireccionResponseDTO responseDTO = new DireccionResponseDTO();

        responseDTO.setIdDireccion(direccionGuardada.getIdDireccion());
        responseDTO.setCalle(direccionGuardada.getCalle());
        responseDTO.setNumero(direccionGuardada.getNumero());
        responseDTO.setCiudad(direccionGuardada.getCiudad());
        responseDTO.setProvincia(direccionGuardada.getProvincia());
        responseDTO.setCodigoPostal(direccionGuardada.getCodigoPostal());

        return responseDTO;
    }

    @Override
    public List<DireccionResponseDTO> listarDirecciones(){
        List<Direccion> direcciones = direccionRepository.findAll();

        return direcciones.stream()
                .map(direccion -> {
                    DireccionResponseDTO responseDTO = new DireccionResponseDTO();

                    responseDTO.setIdDireccion(direccion.getIdDireccion());
                    responseDTO.setCalle(direccion.getCalle());
                    responseDTO.setNumero(direccion.getNumero());
                    responseDTO.setCiudad(direccion.getCiudad());
                    responseDTO.setProvincia(direccion.getProvincia());
                    responseDTO.setCodigoPostal(direccion.getCodigoPostal());

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public DireccionResponseDTO buscarDireccionPorId(Long id){
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Dirección no encontrada"));

        DireccionResponseDTO responseDTO = new DireccionResponseDTO();

        responseDTO.setIdDireccion(direccion.getIdDireccion());
        responseDTO.setCalle(direccion.getCalle());
        responseDTO.setNumero(direccion.getNumero());
        responseDTO.setCiudad(direccion.getCiudad());
        responseDTO.setProvincia(direccion.getProvincia());
        responseDTO.setCodigoPostal(direccion.getCodigoPostal());

        return responseDTO;
    }

    @Override
    public DireccionResponseDTO actualizarDireccion(Long id, DireccionRequestDTO requestDTO){
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Dirección no encontrada"));

        direccion.setCalle(requestDTO.getCalle());
        direccion.setNumero(requestDTO.getNumero());
        direccion.setCiudad(requestDTO.getCiudad());
        direccion.setProvincia(requestDTO.getProvincia());
        direccion.setCodigoPostal(requestDTO.getCodigoPostal());

        Direccion direccionActualizada = direccionRepository.save(direccion);

        DireccionResponseDTO responseDTO = new DireccionResponseDTO();

        responseDTO.setIdDireccion(direccionActualizada.getIdDireccion());
        responseDTO.setCalle(direccionActualizada.getCalle());
        responseDTO.setNumero(direccionActualizada.getNumero());
        responseDTO.setCiudad(direccionActualizada.getCiudad());
        responseDTO.setProvincia(direccionActualizada.getProvincia());
        responseDTO.setCodigoPostal(direccionActualizada.getCodigoPostal());

        return responseDTO;
    }

    @Override
    public DireccionResponseDTO eliminarDireccion(Long id){
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Dirección no encontrada"));

        direccionRepository.deleteById(id);

        DireccionResponseDTO responseDTO = new DireccionResponseDTO();

        responseDTO.setIdDireccion(direccion.getIdDireccion());
        responseDTO.setCalle(direccion.getCalle());
        responseDTO.setNumero(direccion.getNumero());
        responseDTO.setCiudad(direccion.getCiudad());
        responseDTO.setProvincia(direccion.getProvincia());
        responseDTO.setCodigoPostal(direccion.getCodigoPostal());

        return responseDTO;
    }
}
