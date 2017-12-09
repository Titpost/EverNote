package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.service.Implementations.PadServiceImpl;

import com.epam.evernote.service.Interfaces.PadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Configuration for Notepad Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class IntegralPadServiceConfig extends IntegralServiceConfig {

    private static EmbeddedDatabase db = null;


    // Pad beans
    @Bean
    public PadService padService() {
        return new PadServiceImpl();
    }

    @Bean
    public JdbcTemplatePadDao jdbcPadDao() {
        return new JdbcTemplatePadDao();
    }

    @Lazy
    @Bean
    @Override
    public EmbeddedDatabase embeddedDatabase() {
        if (null == db) {
            db = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("createPersonServiceTestSchema.sql")
                    .build();
        }

        return db;
    }
}
