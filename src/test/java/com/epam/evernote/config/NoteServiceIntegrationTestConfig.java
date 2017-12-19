package com.epam.evernote.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Note Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class NoteServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {}
