package com.cgdp.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {

    @Bean
    public BookMapper bookMapper() {
        try {
            return BookMapper.INSTANCE;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

