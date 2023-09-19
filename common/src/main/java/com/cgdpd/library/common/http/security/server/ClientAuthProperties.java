package com.cgdpd.library.common.http.security.server;

import com.cgdpd.library.common.type.Password;

import java.util.List;

public interface ClientAuthProperties {

    String username();

    Password password();

    List<Role> roles();
}
