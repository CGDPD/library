package com.cgdpd.library.catalog.app.config.security;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.common.http.security.server.ClientAuthProperties;
import com.cgdpd.library.common.http.security.server.Role;

public record ClientAuthPropertiesRecord(String username,
                                         String password,
                                         Role role)
      implements ClientAuthProperties {

    public ClientAuthPropertiesRecord {
        requiredNotBlank("username", username);
        requiredNotBlank("password", password);
        required("role", role);
    }
}
