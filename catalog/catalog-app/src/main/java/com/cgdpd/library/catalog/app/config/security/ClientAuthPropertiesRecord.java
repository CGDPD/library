package com.cgdpd.library.catalog.app.config.security;

import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.common.validation.Validator.requiredNotEmpty;

import com.cgdpd.library.common.http.security.server.ClientAuthProperties;
import com.cgdpd.library.common.http.security.server.Role;

import java.util.List;

public record ClientAuthPropertiesRecord(String username,
                                         String password,
                                         List<Role> roles)
      implements ClientAuthProperties {

    public ClientAuthPropertiesRecord {
        requiredNotBlank("username", username);
        requiredNotBlank("password", password);
        requiredNotEmpty("roles", roles);
    }
}
