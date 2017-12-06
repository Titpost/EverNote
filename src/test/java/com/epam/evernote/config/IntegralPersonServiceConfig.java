package com.epam.evernote.config;

import com.epam.evernote.JdbcTemplatePersonDao;
import com.epam.evernote.dao.PersonDao;
import com.epam.evernote.service.PersonService;

import org.springframework.context.annotation.Bean;
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
    public PersonService personService() {
        return new PersonService();
    }

    @Bean
    public PersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }

    @Bean
    public DataSource h2DataSource() {
        if (null == db) {
            db = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("createPersonTable.sql") //script to create person table
                    .build();
        }

        return db;
    }
}
