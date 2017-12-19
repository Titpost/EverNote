package com.epam.evernote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 *  DataBase beans.
 */
public class DataBaseConfig {

    private static EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create-test-db.sql")
                .addScript("insert-test-data.sql")
                .build();

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDatabase());
    }

    @Lazy
    @Bean
    public EmbeddedDatabase embeddedDatabase() {
        return embeddedDatabase;
    }
}
