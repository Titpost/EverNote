package com.epam.evernote.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Config for Controller Integration Tests
 */
public class ApiControllerIntegrationTest {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
