package cl.duoc.sistema_guias_despacho.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar CSRF (necesario para APIs REST sin estado)
            .csrf(csrf -> csrf.disable())
            
            // 2. Configurar los permisos por ruta y rol
            .authorizeHttpRequests(authz -> authz
                // Rol exclusivo que permita solo usar el endpoint de Descargar guías
                .requestMatchers("/api/guias/*/descargar").hasAuthority("SCOPE_Descargas")
                
                // Rol que permita el uso del resto de endpoints (Crear, Modificar, Eliminar, Buscar)
                .requestMatchers("/api/guias/**").hasAuthority("SCOPE_Operaciones")
                
                // Cualquier otra petición debe estar autenticada
                .anyRequest().authenticated()
            )
            
            // 3. Habilitar la validación de tokens JWT (Azure AD B2C actuará como emisor)
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
            
        return http.build();
    }
}