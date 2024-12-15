package br.api.laudocs.laudocs_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/usuario/**").permitAll() // Libera o acesso ao endpoint correto
                .anyRequest().authenticated() // Requer autenticação para outros endpoints
            )
            .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF (usando Lambda DSL)
            .httpBasic(httpBasic -> httpBasic.disable()); // Desabilita autenticação HTTP Basic

        return http.build();
    }
}
