package com.cgdpd.library.frontendapi.config.server;

import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.common.http.security.client.BasicAuthProperties;


public record BasicAuthPropertiesRecord(String username, String password)
      implements BasicAuthProperties {

    public BasicAuthPropertiesRecord {
        requiredNotBlank("username", username);
        requiredNotBlank("password", password);
    }
}
