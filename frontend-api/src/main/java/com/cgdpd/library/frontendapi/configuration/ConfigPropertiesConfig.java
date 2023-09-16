package com.cgdpd.library.frontendapi.configuration;

import com.cgdpd.library.frontendapi.configuration.client.CatalogClientProperties;
import com.cgdpd.library.frontendapi.configuration.security.FrontendApiSecurityProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CatalogClientProperties.class, FrontendApiSecurityProperties.class})
public class ConfigPropertiesConfig {
}
