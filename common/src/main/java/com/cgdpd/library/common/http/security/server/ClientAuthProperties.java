package com.cgdpd.library.common.http.security.server;

public interface ClientAuthProperties {

    String username();

    String password();

    Role role();
}
