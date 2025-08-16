package com.rahul.bugsage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class configures Spring Security for the application,
 * setting up authentication and authorization rules, as well as JWT handling.
 */
@Configuration
public class SecurityConfig {

    // These fields are autowired, but their respective beans must be defined
    // elsewhere in your project (e.g., a JwtAuthenticationFilter and
    // JwtAuthenticationEntryPoint class).
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Allow specific public endpoints to be accessed without authentication
                .requestMatchers(
                    "/api/otp/send",
                    "/api/otp/verify",
                    "/api/otp/test",
                    "/api/auth/register",
                    "/api/auth/login",
                    "/api/auth/hello",
                    "/api/bug/**"
                ).permitAll()
                // All other requests must be authenticated
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                // Set custom entry point for handling authentication errors (401)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                // Use stateless sessions as we are using JWT
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // Add the custom JWT filter before the standard authentication filter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
