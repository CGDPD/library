package com.cgdpd.library.common.http.security.server;

import java.util.List;

public interface ClientAuthProperties {

    String username();

    String password();

    List<Role> roles();
}
