package com.bressan.backend.config;

import com.bressan.backend.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Define los componentes de autenticación utilizados por Spring Security.
 * Conecta la búsqueda de usuarios por email con BCrypt y el proveedor de autenticación.
 */
@Configuration
public class AppConfig {

    private final UsuarioRepository usuarioRepository;

    public AppConfig(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Resuelve los usuarios por email para que Spring Security pueda autenticarlos.
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    /**
     * Asocia la consulta de usuarios con la comparación de contraseñas BCrypt.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}