package com.cgdpd.library.frontendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class FrontendApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApiApplication.class, args);
    }
}
