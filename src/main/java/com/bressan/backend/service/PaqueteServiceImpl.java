package com.bressan.backend.service;

import com.bressan.backend.exception.RecursoNoEncontradoException;
import com.bressan.backend.model.dto.PaqueteRequestDTO;
import com.bressan.backend.model.dto.PaqueteResponseDTO;
import com.bressan.backend.model.entity.Envio;
import com.bressan.backend.model.entity.Paquete;
import com.bressan.backend.repository.EnvioRepository;
import com.bressan.backend.repository.PaqueteRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementa las operaciones de negocio sobre paquetes.
 * Invalida la caché del listado de envíos cuando se modifica información que puede afectarlo.
 */
@Service
@Transactional
public class PaqueteServiceImpl implements PaqueteService {

    private final PaqueteRepository paqueteRepository;
    private final EnvioRepository envioRepository;

    public PaqueteServiceImpl(PaqueteRepository paqueteRepository, EnvioRepository envioRepository){
        this.paqueteRepository = paqueteRepository;
        this.envioRepository = envioRepository;
    }

    /**
     * Guarda el paquete con un envío opcional e invalida la caché de envíos.
     * El listado de envíos incluye cantidadPaquetes, por lo que depende también de los paquetes.
     */
    @Override
    @CacheEvict(value = "envios", allEntries = true)
    public PaqueteResponseDTO guardarPaquete(PaqueteRequestDTO requestDTO){
        Paquete paquete = new Paquete();

        paquete.setDescripcion(requestDTO.getDescripcion());
        paquete.setPeso(requestDTO.getPeso());
        paquete.setAlto(requestDTO.getAlto());
        paquete.setAncho(requestDTO.getAncho());
        paquete.setLargo(requestDTO.getLargo());

        Envio envio = null;

        if(requestDTO.getIdEnvio() != null){
            envio = envioRepository.findById(requestDTO.getIdEnvio())
                    .orElseThrow(() ->
                            new RecursoNoEncontradoException("Envio no encontrado"));
        }

        paquete.setEnvio(envio);

        Paquete paqueteGuardado = paqueteRepository.save(paquete);

        PaqueteResponseDTO responseDTO = new PaqueteResponseDTO();

        responseDTO.setIdPaquete(paqueteGuardado.getIdPaquete());
        responseDTO.setDescripcion(paqueteGuardado.getDescripcion());
        responseDTO.setPeso(paqueteGuardado.getPeso());
        responseDTO.setAlto(paqueteGuardado.getAlto());
        responseDTO.setAncho(paqueteGuardado.getAncho());
        responseDTO.setLargo(paqueteGuardado.getLargo());
        if(paqueteGuardado.getEnvio() != null){
            responseDTO.setIdEnvio(paqueteGuardado.getEnvio().getIdEnvio());
        }

        return responseDTO;
    }

    @Override
    public List<PaqueteResponseDTO> listarPaquetes(){
        List<Paquete> paquetes = paqueteRepository.findAll();

        return paquetes.stream()
                .map(paquete -> {
                    PaqueteResponseDTO responseDTO = new PaqueteResponseDTO();

                    responseDTO.setIdPaquete(paquete.getIdPaquete());
                    responseDTO.setDescripcion(paquete.getDescripcion());
                    responseDTO.setPeso(paquete.getPeso());
                    responseDTO.setAlto(paquete.getAlto());
                    responseDTO.setAncho(paquete.getAncho());
                    responseDTO.setLargo(paquete.getLargo());
                    if(paquete.getEnvio() != null){
                        responseDTO.setIdEnvio(paquete.getEnvio().getIdEnvio());
                    }

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public PaqueteResponseDTO buscarPaquetePorId(Long id){
        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paquete no encontrado"));

        PaqueteResponseDTO responseDTO = new PaqueteResponseDTO();

        responseDTO.setIdPaquete(paquete.getIdPaquete());
        responseDTO.setDescripcion(paquete.getDescripcion());
        responseDTO.setPeso(paquete.getPeso());
        responseDTO.setAlto(paquete.getAlto());
        responseDTO.setAncho(paquete.getAncho());
        responseDTO.setLargo(paquete.getLargo());
        if(paquete.getEnvio() != null){
            responseDTO.setIdEnvio(paquete.getEnvio().getIdEnvio());
        }

        return responseDTO;
    }

    /**
     * Actualiza el paquete e invalida el listado de envíos que puede verse afectado.
     */
    @Override
    @CacheEvict(value = "envios", allEntries = true)
    public PaqueteResponseDTO actualizarPaquete(Long id, PaqueteRequestDTO requestDTO){
        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paquete no encontrado"));

        paquete.setDescripcion(requestDTO.getDescripcion());
        paquete.setPeso(requestDTO.getPeso());
        paquete.setAlto(requestDTO.getAlto());
        paquete.setAncho(requestDTO.getAncho());
        paquete.setLargo(requestDTO.getLargo());
        Envio envio = null;

        if(requestDTO.getIdEnvio() != null){
            envio = envioRepository.findById(requestDTO.getIdEnvio())
                    .orElseThrow(() ->
                            new RecursoNoEncontradoException("Envio no encontrado"));
        }

        paquete.setEnvio(envio);

        Paquete paqueteActualizado = paqueteRepository.save(paquete);

        PaqueteResponseDTO responseDTO = new PaqueteResponseDTO();

        responseDTO.setIdPaquete(paqueteActualizado.getIdPaquete());
        responseDTO.setDescripcion(paqueteActualizado.getDescripcion());
        responseDTO.setPeso(paqueteActualizado.getPeso());
        responseDTO.setAlto(paqueteActualizado.getAlto());
        responseDTO.setAncho(paqueteActualizado.getAncho());
        responseDTO.setLargo(paqueteActualizado.getLargo());
        if(paqueteActualizado.getEnvio() != null){
            responseDTO.setIdEnvio(paqueteActualizado.getEnvio().getIdEnvio());
        }

        return responseDTO;
    }

    /**
     * Elimina el paquete e invalida el listado de envíos que puede verse afectado.
     */
    @Override
    @CacheEvict(value = "envios", allEntries = true)
    public PaqueteResponseDTO eliminarPaquete(Long id){
        Paquete paquete = paqueteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paquete no encontrado"));

        paqueteRepository.deleteById(id);

        PaqueteResponseDTO responseDTO = new PaqueteResponseDTO();

        responseDTO.setIdPaquete(paquete.getIdPaquete());
        responseDTO.setDescripcion(paquete.getDescripcion());
        responseDTO.setPeso(paquete.getPeso());
        responseDTO.setAlto(paquete.getAlto());
        responseDTO.setAncho(paquete.getAncho());
        responseDTO.setLargo(paquete.getLargo());
        if(paquete.getEnvio() != null){
            responseDTO.setIdEnvio(paquete.getEnvio().getIdEnvio());
        }

        return responseDTO;
    }
}