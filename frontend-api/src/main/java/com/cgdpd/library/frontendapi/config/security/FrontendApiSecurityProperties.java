package com.cgdpd.library.frontendapi.config.security;

import com.cgdpd.library.common.http.security.server.HttpSecurityProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "cgdpd.http.security")
public record FrontendApiSecurityProperties(Set<String> allowedMethods,
                                            Set<String> allowedOrigins,
                                            Set<String> allowedHeaders)
      implements HttpSecurityProperties {
}
