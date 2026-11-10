package com.bressan.backend.config;

import com.bressan.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;
import com.bressan.backend.security.AccessDeniedHandlerJwt;
import com.bressan.backend.security.AuthenticationEntryPointJwt;

/**
 * Establece las políticas de acceso HTTP y los filtros de seguridad.
 * La autenticación es sin sesión de servidor y utiliza un JWT por petición.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPointJwt authenticationEntryPointJwt;
    private final AccessDeniedHandlerJwt accessDeniedHandlerJwt;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider, AuthenticationEntryPointJwt authenticationEntryPointJwt, AccessDeniedHandlerJwt accessDeniedHandlerJwt){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
        this.authenticationEntryPointJwt = authenticationEntryPointJwt;
        this.accessDeniedHandlerJwt = accessDeniedHandlerJwt;
    }

    /**
     * Aplica las reglas de autenticación y autorización de la API.
     * Las rutas públicas se evalúan antes que la regla general de usuario autenticado.
     * El filtro JWT se ejecuta antes del filtro habitual de usuario y contraseña.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
                        // Las métricas y datos operativos no se exponen a usuarios comunes.
                        .requestMatchers("/actuator/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPointJwt)
                        .accessDeniedHandler(accessDeniedHandlerJwt)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
