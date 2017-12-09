package com.epam.evernote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Configuration for Person Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class PersonServiceTestIntegralTestConfig extends ServiceIntegralTestConfig {

    private static EmbeddedDatabase db = null;

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
