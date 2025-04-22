package com.yhdc.api_gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Swagger
    private final String[] freeResourceUrls = {"/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**",
            "/v3/api-docs/**", "/api-docs/**", "/aggregate/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Swagger
                                .requestMatchers(freeResourceUrls)
                                .permitAll()
                                // Keycloak
                                .anyRequest()
                                .authenticated()).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                .build();
    }

}
