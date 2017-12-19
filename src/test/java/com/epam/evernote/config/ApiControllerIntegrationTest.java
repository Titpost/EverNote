package com.epam.evernote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Config for Controller Integration Tests
 */
public class ApiControllerIntegrationTest {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
