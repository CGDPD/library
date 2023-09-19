package com.cgdpd.library.catalog.app.config;

import com.cgdpd.library.catalog.app.config.security.AuthorizedClientsProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuthorizedClientsProperties.class})
public class PropertiesConfig {
}
