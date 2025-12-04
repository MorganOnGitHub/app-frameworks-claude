package com.space.planetmoonapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/h2-console/**", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()

                        // GraphQL endpoints - Admin only
                        .requestMatchers("/graphql", "/graphiql").hasRole("ADMIN")

                        // Planet endpoints
                        .requestMatchers(HttpMethod.GET, "/api/planets/**").hasAnyRole("ADMIN", "STAFF", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/planets/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.PUT, "/api/planets/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/planets/**").hasAnyRole("ADMIN", "STAFF")

                        // Moon endpoints
                        .requestMatchers(HttpMethod.GET, "/api/moons/**").hasAnyRole("ADMIN", "STAFF", "STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/moons/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.DELETE, "/api/moons/**").hasAnyRole("ADMIN", "STAFF")

                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {})
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .userDetailsService(userDetailsService);

        // Allow H2 console frames
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}