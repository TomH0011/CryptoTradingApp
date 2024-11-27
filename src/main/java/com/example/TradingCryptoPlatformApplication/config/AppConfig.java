package com.example.TradingCryptoPlatformApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class AppConfig {


    // What does this do?
    // Configures stateless authentication (no server-side sessions, relies on JWT).
    // Secures the /api/** endpoints (requires authentication).
    // Allows public access to all other endpoints.
    // Validates JWT tokens using a custom filter.
    // Disables CSRF protection.
    // Enables CORS with specific rules.
    // Returns the security configuration so Spring can apply it.
    // Why Are we doing it?
    // We will want the user or admin to be able to log in and be able to seamlessly move around the webapp securely
    // We use the Bean annotation as we want spring to remember this as it will be called upon frequently
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }
    private CorsConfigurationSource corsConfigurationSource() {
        return null;
    }
}
