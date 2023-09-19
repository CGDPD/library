package com.cgdpd.library.catalog.app.config.security;

import static com.cgdpd.library.common.http.security.server.Role.MANAGEMENT;
import static com.cgdpd.library.common.http.security.server.UserDetailsServiceProvider.inMemoryUserDetailsManager;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import com.cgdpd.library.common.http.security.server.ApiAccessDeniedHandler;
import com.cgdpd.library.common.http.security.server.ApiAuthenticationFailureHandler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Clock;
import java.time.Duration;

@Configuration
@EnableWebSecurity
public class CatalogSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   ApiAuthenticationFailureHandler authenticationFailureHandler,
                                                   ApiAccessDeniedHandler accessDeniedHandler)
          throws Exception {
        return httpSecurity
              .csrf(AbstractHttpConfigurer::disable)
              .cors(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(it ->
                    it.requestMatchers(antMatcher("/management/**"))
                          .hasRole(MANAGEMENT.name())
                          .anyRequest()
                          .authenticated())
              .httpBasic(it -> it.authenticationEntryPoint(authenticationFailureHandler))
              .exceptionHandling(it -> it.accessDeniedHandler(accessDeniedHandler))
              .headers(headersCustomizer())
              .build();
    }

    private Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer() {
        return headersConfigurer -> headersConfigurer
              .contentSecurityPolicy(it -> it.policyDirectives("default-src 'self'"))
              .httpStrictTransportSecurity(it -> it.includeSubDomains(true)
                    .maxAgeInSeconds(Duration.ofDays(365).toSeconds()));
    }

    @Bean
    public UserDetailsService userDetailsService(AuthorizedClientsProperties authorizedClients,
                                                 PasswordEncoder passwordEncoder) {
        return inMemoryUserDetailsManager(authorizedClients.authorizedClients(), passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                            PasswordEncoder passwordEncoder) {
        var authProvider = new DaoAuthenticationProvider(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public ApiAuthenticationFailureHandler authenticationFailureHandler(
          @Qualifier("mappingJackson2HttpMessageConverter")
          MappingJackson2HttpMessageConverter messageConverter,
          Clock clock) {
        return new ApiAuthenticationFailureHandler(messageConverter, clock);
    }

    @Bean
    public ApiAccessDeniedHandler accessDeniedHandler(
          @Qualifier("mappingJackson2HttpMessageConverter")
          MappingJackson2HttpMessageConverter messageConverter,
          Clock clock) {
        return new ApiAccessDeniedHandler(messageConverter, clock);
    }
}

