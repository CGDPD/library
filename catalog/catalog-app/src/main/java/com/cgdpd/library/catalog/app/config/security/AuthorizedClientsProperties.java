package com.cgdpd.library.catalog.app.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "cgdpd.http.server")
public record AuthorizedClientsProperties(

      List<ClientAuthPropertiesRecord> authorizedClients) {
}
