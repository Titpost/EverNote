package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.dao.PersonDao;
import com.epam.evernote.service.Implementations.PersonServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuration for Person Service testing.
 */
public class IntegralPersonServiceConfig {

    private static EmbeddedDatabase db = null;

    @Bean
    public PersonServiceImpl personService() {
        return new PersonServiceImpl();
    }

    @Bean
    public PersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDatabase());
    }

    @Bean
    private EmbeddedDatabase embeddedDatabase() {
        if (null == db) {
            db = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("createPersonTable.sql")
                    .build();
        }

        return db;
    }
}
