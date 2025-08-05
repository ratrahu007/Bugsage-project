package com.rahul.bugsage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ✅ Password encoder bean (used for hashing passwords)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Main security configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // ❌ Disable CSRF (since it's API, not form login)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/otp/send",
                    "/api/otp/verify",
                    "/api/otp/test"
                     // ✅ allow hello test
                ).permitAll() // ✅ public routes
                .anyRequest().authenticated() // 🔐 everything else is secured
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 🚫 no session
            );

        return http.build();
    }
}
