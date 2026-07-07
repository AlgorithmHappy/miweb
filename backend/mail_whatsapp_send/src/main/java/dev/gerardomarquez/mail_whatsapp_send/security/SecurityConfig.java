package dev.gerardomarquez.mail_whatsapp_send.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security para proteger el panel de sync.
 * Solo /sync y sus subrutas requieren autenticación.
 * El resto de la aplicación permanece público.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Value("${sync.panel.username}")
    private String username;

    @Value("${sync.panel.password}")
    private String password;

    /**
     * Define las reglas de acceso y el formulario de login.
     *
     * @param http Configuración de seguridad HTTP
     * @return SecurityFilterChain configurado
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/sync/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/sync/login")
                .loginProcessingUrl("/sync/login")
                .defaultSuccessUrl("/sync", true)
                .failureUrl("/sync/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/sync/logout")
                .logoutSuccessUrl("/sync/login?logout=true")
                .permitAll()
            );

        return http.build();
    }

    /**
     * Usuario único en memoria cargado desde application.properties.
     * La contraseña se encripta con BCrypt.
     *
     * @return InMemoryUserDetailsManager con el usuario configurado
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Encoder BCrypt para la contraseña del usuario.
     *
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
