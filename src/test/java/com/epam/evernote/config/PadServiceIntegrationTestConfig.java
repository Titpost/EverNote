package com.epam.evernote.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Pad Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.implementations",
        "com.epam.evernote.dao"
})
public class PadServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {}
