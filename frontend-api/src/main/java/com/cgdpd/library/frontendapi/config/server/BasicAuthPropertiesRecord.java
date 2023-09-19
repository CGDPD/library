package com.cgdpd.library.frontendapi.config.server;

import static com.cgdpd.library.common.validation.Validator.required;
import static com.cgdpd.library.common.validation.Validator.requiredNotBlank;

import com.cgdpd.library.common.http.security.client.BasicAuthProperties;
import com.cgdpd.library.common.type.Password;


public record BasicAuthPropertiesRecord(String username, Password password)
      implements BasicAuthProperties {

    public BasicAuthPropertiesRecord {
        requiredNotBlank("username", username);
        required("password", password);
    }
}
