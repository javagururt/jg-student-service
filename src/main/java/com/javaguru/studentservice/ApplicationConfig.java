package com.javaguru.studentservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ApplicationConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
