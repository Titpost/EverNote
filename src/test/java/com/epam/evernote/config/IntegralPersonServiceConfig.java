package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.service.Implementations.PersonServiceImpl;

import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * Configuration for Person Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class IntegralPersonServiceConfig {

    private static EmbeddedDatabase db = null;


    // Person beans
    @Bean
    public PersonService personService() {
        return new PersonServiceImpl();
    }

    @Bean
    public JdbcTemplatePersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }

    // DataBase beans
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(embeddedDatabase());
    }

    @Lazy
    @Bean
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
