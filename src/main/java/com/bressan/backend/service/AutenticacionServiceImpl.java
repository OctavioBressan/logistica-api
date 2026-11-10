package com.bressan.backend.service;

import com.bressan.backend.model.dto.AutenticacionRequestDTO;
import com.bressan.backend.model.dto.AutenticacionResponseDTO;
import com.bressan.backend.model.dto.RegistroRequestDTO;
import com.bressan.backend.model.entity.Role;
import com.bressan.backend.model.entity.Usuario;
import com.bressan.backend.repository.UsuarioRepository;
import com.bressan.backend.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementa el registro y el inicio de sesión de usuarios.
 * Las contraseñas se codifican con BCrypt antes de almacenarse.
 */
@Service
@Transactional
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AutenticacionServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registra un usuario nuevo con autoridad USER y devuelve su JWT.
     * El rol no se toma del DTO público, evitando que el registro asigne ADMIN.
     */
    @Override
    public AutenticacionResponseDTO registrarUsuario(RegistroRequestDTO requestDTO){
        Usuario usuario = new Usuario();

        usuario.setNombre(requestDTO.getNombre());
        usuario.setApellido(requestDTO.getApellido());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        usuario.setRole(Role.USER);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuarioGuardado);

        return new AutenticacionResponseDTO(token);
    }

    /**
     * Valida las credenciales con AuthenticationManager y genera un JWT.
     */
    @Override
    public AutenticacionResponseDTO autenticarUsuario(AutenticacionRequestDTO requestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow();

        String token = jwtService.generarToken(usuario);

        return new AutenticacionResponseDTO(token);
    }
}