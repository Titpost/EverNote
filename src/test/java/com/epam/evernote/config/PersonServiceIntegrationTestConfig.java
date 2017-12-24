package com.epam.evernote.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Person Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.implementations",
        "com.epam.evernote.dao"
})
public class PersonServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {}
