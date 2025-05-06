package com.yhdc.api_gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    // Swagger
    private final String[] freeResourceUrls = {"/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**",
            "/v3/api-docs/**", "/api-docs/**", "/aggregate/**"};

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(freeResourceUrls)
                        .permitAll()
                        .anyExchange()
                        .authenticated()
                )
                // Keycloak
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()
                        )
                )
                .build();
    }

}
