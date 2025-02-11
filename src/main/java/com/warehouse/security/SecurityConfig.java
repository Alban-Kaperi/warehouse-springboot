package com.warehouse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
* The `SecurityConfig` class is a `Spring Security configuration class` that sets up the application's security functionality,
* including authentication and authorization mechanisms.
* Each annotated component and method serves a specific part in configuring application security.
*/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private JWTAuthEntryPoint authEntryPoint;
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JWTAuthEntryPoint authEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authEntryPoint = authEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*
    * SecurityFilterChain method configures the security filter chain used in Spring Security.
    * It uses the `HttpSecurity` object to define security behaviors, such as handling CSRF, session management, and access control.
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                // Intercept unauthorized access attempts and respond with a standardized `401 Unauthorized` HTTP status code.
                .exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authEntryPoint))
                // Make the session stateless for api usage
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Allow routes under /api/auth/ and require authentication for all others.
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated());

        // Intercept the token and verify user details
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
