package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.dao.PadDao;
import com.epam.evernote.dao.PersonDao;
import com.epam.evernote.service.Implementations.PadServiceImpl;

import com.epam.evernote.service.Implementations.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Configuration for Notepad Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class IntegralPadServiceConfig {

    private static EmbeddedDatabase db = null;


    @Bean
    public PersonServiceImpl personService() {
        return new PersonServiceImpl();
    }

    @Bean
    public PadServiceImpl padService() {
        return new PadServiceImpl();
    }


    @Bean
    public PersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }

    @Bean
    public PadDao jdbcPadDao() {
        return new JdbcTemplatePadDao();
    }


    @Bean
    public DataSource h2DataSource() {
        if (null == db) {
            db = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("createPersonServiceTestSchema.sql")
                    .build();
        }

        return db;
    }}
