package com.trentonrush.inventoryservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Config
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        // Authorization rules for slabs
                        .requestMatchers(HttpMethod.POST, "/v1/slabs").hasAuthority("SCOPE_add:slabs")
                        .requestMatchers(HttpMethod.PUT, "/v1/slabs/**").hasAuthority("SCOPE_update:slabs")
                        .requestMatchers(HttpMethod.PATCH, "/v1/slabs/**").hasAuthority("SCOPE_update:slabs")
                        .requestMatchers(HttpMethod.DELETE, "/v1/slabs/**").hasAuthority("SCOPE_delete:slabs")

                        // Authorization rules for prices
                        .requestMatchers(HttpMethod.POST, "/v1/prices").hasAuthority("SCOPE_add:prices")
                        .requestMatchers(HttpMethod.PUT, "/v1/prices/**").hasAuthority("SCOPE_update:prices")
                        .requestMatchers(HttpMethod.PATCH, "/v1/prices/**").hasAuthority("SCOPE_update:prices")
                        .requestMatchers(HttpMethod.DELETE, "/v1/prices/**").hasAuthority("SCOPE_delete:prices")

                        // Authorization rules for samples
                        .requestMatchers(HttpMethod.POST, "/v1/samples").hasAuthority("SCOPE_add:samples")
                        .requestMatchers(HttpMethod.PUT, "/v1/samples/**").hasAuthority("SCOPE_update:samples")
                        .requestMatchers(HttpMethod.PATCH, "/v1/samples/**").hasAuthority("SCOPE_update:samples")
                        .requestMatchers(HttpMethod.DELETE, "/v1/samples/**").hasAuthority("SCOPE_delete:samples")

                        // Authorized GET requests
                        .requestMatchers(HttpMethod.GET, "/v1/**").authenticated()

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> {})
                ).build();
    }
}
