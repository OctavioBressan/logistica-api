package com.bressan.backend.service;

import com.bressan.backend.exception.RecursoNoEncontradoException;
import com.bressan.backend.model.dto.EnvioRequestDTO;
import com.bressan.backend.model.dto.EnvioResponseDTO;
import com.bressan.backend.model.entity.Cliente;
import com.bressan.backend.model.entity.Direccion;
import com.bressan.backend.model.entity.Envio;
import com.bressan.backend.model.entity.Transportista;
import com.bressan.backend.repository.ClienteRepository;
import com.bressan.backend.repository.DireccionRepository;
import com.bressan.backend.repository.EnvioRepository;
import com.bressan.backend.repository.TransportistaRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementa las operaciones de negocio sobre envíos.
 * Aplica una consulta optimizada para el listado y mantiene su caché sincronizada mediante invalidaciones.
 */
@Service
@Transactional
public class EnvioServiceImpl implements EnvioService {

    private final EnvioRepository envioRepository;
    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;
    private final TransportistaRepository transportistaRepository;

    public EnvioServiceImpl(EnvioRepository envioRepository, ClienteRepository clienteRepository, DireccionRepository direccionRepository,
                            TransportistaRepository transportistaRepository){
        this.envioRepository = envioRepository;
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
        this.transportistaRepository = transportistaRepository;
    }

    /**
     * Registra un envío y resuelve las relaciones mediante sus identificadores.
     * Al cambiar los datos, se invalida el listado de envíos cacheado.
     */
    @Override
    @CacheEvict(value = "envios", allEntries = true)
    public EnvioResponseDTO guardarEnvio(EnvioRequestDTO requestDTO){
        Envio envio = new Envio();

        envio.setCodigoSeguimiento(requestDTO.getCodigoSeguimiento());
        envio.setFechaEnvio(requestDTO.getFechaEnvio());
        envio.setEstado(requestDTO.getEstado());
        Transportista transportista = null;

        if(requestDTO.getIdTransportista() != null){
            transportista = transportistaRepository.findById(requestDTO.getIdTransportista())
                    .orElseThrow(() ->
                            new RecursoNoEncontradoException("Transportista no encontrado"));
        }

        envio.setTransportista(transportista);
        Cliente cliente = clienteRepository.findById(requestDTO.getIdCliente())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Cliente no encontrado"));

        envio.setCliente(cliente);
        Direccion direccionOrigen = direccionRepository.findById(requestDTO.getIdDireccionOrigen())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Dirección de origen no encontrada"));

        envio.setDireccionOrigen(direccionOrigen);
        Direccion direccionDestino = direccionRepository.findById(requestDTO.getIdDireccionDestino())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Dirección de destino no encontrada"));

        envio.setDireccionDestino(direccionDestino);

        Envio envioGuardado = envioRepository.save(envio);

        EnvioResponseDTO responseDTO = new EnvioResponseDTO();

        responseDTO.setIdEnvio(envioGuardado.getIdEnvio());
        responseDTO.setCodigoSeguimiento(envioGuardado.getCodigoSeguimiento());
        responseDTO.setFechaEnvio(envioGuardado.getFechaEnvio());
        responseDTO.setEstado(envioGuardado.getEstado());
        if(envio.getTransportista() != null){
            responseDTO.setIdTransportista(
                    envio.getTransportista().getIdTransportista()
            );
        }
        responseDTO.setIdCliente(envioGuardado.getCliente().getIdCliente());
        responseDTO.setIdDireccionOrigen(envioGuardado.getDireccionOrigen().getIdDireccion());
        responseDTO.setIdDireccionDestino(envioGuardado.getDireccionDestino().getIdDireccion());

        return responseDTO;
    }

    /**
     * Obtiene el listado de envíos y calcula la cantidad de paquetes para cada DTO.
     * La primera ejecución consulta la base; las siguientes pueden devolver la caché envios.
     */
    @Override
    @Cacheable(value = "envios")
    public List<EnvioResponseDTO> listarEnvios(){
        // La consulta con EntityGraph carga paquetes junto con los envíos y evita el patrón N+1.
        List<Envio> envios = envioRepository.findAllBy();

        return envios.stream()
                .map(envio -> {
                    EnvioResponseDTO responseDTO = new EnvioResponseDTO();

                    responseDTO.setIdEnvio(envio.getIdEnvio());
                    responseDTO.setCodigoSeguimiento(envio.getCodigoSeguimiento());
                    responseDTO.setFechaEnvio(envio.getFechaEnvio());
                    responseDTO.setEstado(envio.getEstado());
                    if(envio.getTransportista() != null){
                        responseDTO.setIdTransportista(
                                envio.getTransportista().getIdTransportista()
                        );
                    }
                    responseDTO.setIdCliente(envio.getCliente().getIdCliente());
                    responseDTO.setIdDireccionOrigen(envio.getDireccionOrigen().getIdDireccion());
                    responseDTO.setIdDireccionDestino(envio.getDireccionDestino().getIdDireccion());
                    // El dato se calcula para la respuesta; no es una columna adicional del envío.
                    responseDTO.setCantidadPaquetes(envio.getPaquetes().size());

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public EnvioResponseDTO buscarEnvioPorId(Long id){
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envío no encontrado"));

        EnvioResponseDTO responseDTO = new EnvioResponseDTO();

        responseDTO.setIdEnvio(envio.getIdEnvio());
        responseDTO.setCodigoSeguimiento(envio.getCodigoSeguimiento());
        responseDTO.setFechaEnvio(envio.getFechaEnvio());
        responseDTO.setEstado(envio.getEstado());
        if(envio.getTransportista() != null){
            responseDTO.setIdTransportista(
                    envio.getTransportista().getIdTransportista()
            );
        }
        responseDTO.setIdCliente(envio.getCliente().getIdCliente());
        responseDTO.setIdDireccionOrigen(envio.getDireccionOrigen().getIdDireccion());
        responseDTO.setIdDireccionDestino(envio.getDireccionDestino().getIdDireccion());

        return responseDTO;
    }

    /**
     * Actualiza los datos del envío y evita conservar un listado desactualizado.
     */
    @Override
    @CacheEvict(value = "envios", allEntries = true)
    public EnvioResponseDTO actualizarEnvio(Long id, EnvioRequestDTO requestDTO){
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Envío no encontrado"));

        envio.setCodigoSeguimiento(requestDTO.getCodigoSeguimiento());
        envio.setFechaEnvio(requestDTO.getFechaEnvio());
        envio.setEstado(requestDTO.getEstado());

        Transportista transportista = null;

        if(requestDTO.getIdTransportista() != null){
            transportista = transportistaRepository.findById(requestDTO.getIdTransportista())
                    .orElseThrow(() ->
                            new RecursoNoEncontradoException("Transportista no encontrado"));
        }

        envio.setTransportista(transportista);

        Cliente cliente = clienteRepository.findById(requestDTO.getIdCliente())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Cliente no encontrado"));

        envio.setCliente(cliente);

        Direccion direccionOrigen = direccionRepository.findById(requestDTO.getIdDireccionOrigen())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Dirección de origen no encontrada"));

        envio.setDireccionOrigen(direccionOrigen);
        Direccion direccionDestino = direccionRepository.findById(requestDTO.getIdDireccionDestino())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Dirección de destino no encontrada"));

        envio.setDireccionDestino(direccionDestino);

        Envio envioActualizado = envioRepository.save(envio);

        EnvioResponseDTO responseDTO = new EnvioResponseDTO();

        responseDTO.setIdEnvio(envioActualizado.getIdEnvio());
        responseDTO.setCodigoSeguimiento(envioActualizado.getCodigoSeguimiento());
        responseDTO.setFechaEnvio(envioActualizado.getFechaEnvio());
        responseDTO.setEstado(envioActualizado.getEstado());

        if(envioActualizado.getTransportista() != null){
            responseDTO.setIdTransportista(
                    envioActualizado.getTransportista().getIdTransportista()
            );
        }
        responseDTO.setIdCliente(envioActualizado.getCliente().getIdCliente());
        responseDTO.setIdDireccionOrigen(envioActualizado.getDireccionOrigen().getIdDireccion());
        responseDTO.setIdDireccionDestino(envioActualizado.getDireccionDestino().getIdDireccion());

        return responseDTO;
    }

    /**
     * Elimina el envío solicitado e invalida el listado cacheado.
     */
    @Override
    @CacheEvict(value = "envios", allEntries = true)
    public EnvioResponseDTO eliminarEnvio(Long id){
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envío no encontrado"));

        envioRepository.deleteById(id);

        EnvioResponseDTO responseDTO = new EnvioResponseDTO();

        responseDTO.setIdEnvio(envio.getIdEnvio());
        responseDTO.setCodigoSeguimiento(envio.getCodigoSeguimiento());
        responseDTO.setFechaEnvio(envio.getFechaEnvio());
        responseDTO.setEstado(envio.getEstado());
        if(envio.getTransportista() != null){
            responseDTO.setIdTransportista(
                    envio.getTransportista().getIdTransportista()
            );
        }
        responseDTO.setIdCliente(envio.getCliente().getIdCliente());
        responseDTO.setIdDireccionOrigen(envio.getDireccionOrigen().getIdDireccion());
        responseDTO.setIdDireccionDestino(envio.getDireccionDestino().getIdDireccion());

        return responseDTO;
    }

    @Override
    public List<EnvioResponseDTO> listarEnviosOrdenadosPorFecha(){
        List<Envio> envios = envioRepository.findAllByOrderByFechaEnvioAsc();

        return envios.stream()
                .map(envio -> {
                    EnvioResponseDTO responseDTO = new EnvioResponseDTO();

                    responseDTO.setIdEnvio(envio.getIdEnvio());
                    responseDTO.setCodigoSeguimiento(envio.getCodigoSeguimiento());
                    responseDTO.setFechaEnvio(envio.getFechaEnvio());
                    responseDTO.setEstado(envio.getEstado());
                    if(envio.getTransportista() != null){
                        responseDTO.setIdTransportista(
                                envio.getTransportista().getIdTransportista()
                        );
                    }
                    responseDTO.setIdCliente(envio.getCliente().getIdCliente());
                    responseDTO.setIdDireccionOrigen(envio.getDireccionOrigen().getIdDireccion());
                    responseDTO.setIdDireccionDestino(envio.getDireccionDestino().getIdDireccion());

                    return responseDTO;
                })
                .toList();
    }

    @Override
    public List<EnvioResponseDTO> listarEnviosPorCliente(Long idCliente){

        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        List<Envio> envios = envioRepository.findByCliente_IdCliente(idCliente);

        return envios.stream()
                .map(envio -> {
                    EnvioResponseDTO responseDTO = new EnvioResponseDTO();

                    responseDTO.setIdEnvio(envio.getIdEnvio());
                    responseDTO.setCodigoSeguimiento(envio.getCodigoSeguimiento());
                    responseDTO.setFechaEnvio(envio.getFechaEnvio());
                    responseDTO.setEstado(envio.getEstado());
                    if(envio.getTransportista() != null){
                        responseDTO.setIdTransportista(
                                envio.getTransportista().getIdTransportista()
                        );
                    }
                    responseDTO.setIdCliente(envio.getCliente().getIdCliente());
                    responseDTO.setIdDireccionOrigen(envio.getDireccionOrigen().getIdDireccion());
                    responseDTO.setIdDireccionDestino(envio.getDireccionDestino().getIdDireccion());

                    return responseDTO;
                })
                .toList();
    }
}