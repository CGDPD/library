package com.cgdpd.library.common.http.security.client;

import com.cgdpd.library.common.type.Password;

public interface BasicAuthProperties {

    String username();

    Password password();
}
