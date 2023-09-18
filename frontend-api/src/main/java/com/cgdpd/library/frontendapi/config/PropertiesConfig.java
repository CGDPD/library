package com.cgdpd.library.frontendapi.config;

import com.cgdpd.library.frontendapi.config.server.CatalogClientProperties;
import com.cgdpd.library.frontendapi.config.security.FrontendApiSecurityProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CatalogClientProperties.class, FrontendApiSecurityProperties.class})
public class PropertiesConfig {
}
