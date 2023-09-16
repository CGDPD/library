package com.cgdpd.library.frontendapi.configuration.security;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.cgdpd.library.common.http.RegexCorsConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   FrontendApiSecurityProperties properties)
          throws Exception {
        return httpSecurity
              .csrf(withDefaults())
              .cors(corsCustomizer(properties))
              .authorizeHttpRequests(it -> it.requestMatchers(antMatcher("/view/**")).permitAll())
              .headers(headersCustomizer())
              .build();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer(
          FrontendApiSecurityProperties properties) {
        return corsConfigurer -> corsConfigurer
              .configurationSource(corsConfigurationSource(properties));
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource(
          FrontendApiSecurityProperties properties) {
        var configuration = new RegexCorsConfiguration();
        setupCorsConfiguration(configuration, properties);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void setupCorsConfiguration(RegexCorsConfiguration config,
                                        FrontendApiSecurityProperties properties) {
        config.setAllowedOrigins(properties.allowedOrigins().stream().toList());
        config.setAllowedMethods(properties.allowedMethods().stream().toList());
        config.setAllowedHeaders(properties.allowedHeaders().stream().toList());
    }

    private Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer() {
        return headersConfigurer -> headersConfigurer
              .contentSecurityPolicy(it -> it.policyDirectives("default-src 'self'"))
              .xssProtection(xss -> xss.headerValue(ENABLED_MODE_BLOCK))
              .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
              .httpStrictTransportSecurity(it -> it.includeSubDomains(true)
                    .maxAgeInSeconds(Duration.ofDays(365).toSeconds()));
    }
}

