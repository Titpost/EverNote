package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.service.Implementations.PadServiceImpl;
import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Pad Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class PadServiceIntegralTestConfig extends ServiceIntegralTestConfig {


    // Pad beans
    @Bean
    public PadService padService() {
        return new PadServiceImpl();
    }

    @Bean
    public JdbcTemplatePadDao jdbcPadDao() {
        return new JdbcTemplatePadDao();
    }

}
