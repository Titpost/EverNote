package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Pad Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class PadServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {

    public PadService padService;

    public JdbcTemplatePadDao jdbcPadDao;
}
