package com.epam.evernote.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Tag Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class TagServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {}
