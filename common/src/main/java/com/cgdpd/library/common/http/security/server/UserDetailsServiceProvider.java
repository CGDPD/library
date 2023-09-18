package com.cgdpd.library.common.http.security.server;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashSet;
import java.util.List;

public class UserDetailsServiceProvider {

    public static InMemoryUserDetailsManager inMemoryUserDetailsManager(
          List<? extends ClientAuthProperties> clients,
          PasswordEncoder passwordEncoder) {
        var users = new HashSet<UserDetails>();
        for (var client : clients) {
            var user = User.builder()
                  .username(client.username())
                  .password(passwordEncoder.encode(client.password()))
                  .roles(client.role().name())
                  .build();
            users.add(user);
        }
        return new InMemoryUserDetailsManager(users);
    }
}
