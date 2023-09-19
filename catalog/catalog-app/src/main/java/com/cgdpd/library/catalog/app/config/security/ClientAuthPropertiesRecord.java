package com.cgdpd.library.catalog.app.config.security;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;
import static com.cgdpd.library.common.validation.Validator.requiredNotEmpty;

import com.cgdpd.library.common.http.security.server.ClientAuthProperties;
import com.cgdpd.library.common.http.security.server.Role;
import com.cgdpd.library.common.type.Password;

import java.util.List;

public record ClientAuthPropertiesRecord(String username,
                                         Password password,
                                         List<Role> roles)
      implements ClientAuthProperties {

    public ClientAuthPropertiesRecord {
        requiredNotBlank("username", username);
        required("password", password);
        requiredNotEmpty("roles", roles);
    }
}
