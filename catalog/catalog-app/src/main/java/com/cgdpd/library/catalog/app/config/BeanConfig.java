package com.cgdpd.library.catalog.app.config;

import static com.cgdpd.library.common.provider.ClockProvider.utcClock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class BeanConfig {

    @Bean
    public Clock clock() {
        return utcClock();
    }
}
