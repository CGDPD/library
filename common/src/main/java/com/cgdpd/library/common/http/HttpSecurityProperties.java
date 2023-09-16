package com.cgdpd.library.common.http;

import java.util.Set;

public interface HttpSecurityProperties {

    Set<String> allowedMethods();

    Set<String> allowedOrigins();

    Set<String> allowedHeaders();
}
