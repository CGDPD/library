package com.cgdpd.library.common.http.security.server;

import java.util.Set;

public interface HttpSecurityProperties {

    Set<String> allowedMethods();

    Set<String> allowedOrigins();

    Set<String> allowedHeaders();
}
