package br.api.laudocs.laudocs_api.config;

import br.api.laudocs.laudocs_api.service.UserDetailsServiceImpl;
import br.api.laudocs.laudocs_api.api.filters.AuthorizationFilter;
import br.api.laudocs.laudocs_api.api.filters.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*"); // Permite todas as origens (ajuste conforme necessário)
        corsConfig.addAllowedMethod("*"); // Permite todos os métodos HTTP (ajuste conforme necessário)
        corsConfig.addAllowedHeader("*"); // Permite todos os cabeçalhos (ajuste conforme necessário)
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.POST, "/api/v1/login", "/api/v1/usuario").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/usuario").hasAnyRole( "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/v1/usuario/{userId}").hasAnyRole( "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/usuario/alterar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/usuario/remover/{userId}").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .addFilterBefore(new LoginFilter("/api/v1/login", authenticationManager),
                    UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authenticationManager(authenticationManager)
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .headers(headers ->
                headers.frameOptions(Customizer.withDefaults()).disable() // Desabilita a proteção contra frame options
            );

        return http.build();
    }
}
