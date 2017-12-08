package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.dao.PadDao;
import com.epam.evernote.dao.PersonDao;
import com.epam.evernote.service.Implementations.PadServiceImpl;

import com.epam.evernote.service.Implementations.PersonServiceImpl;
import com.epam.evernote.service.Interfaces.PadService;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
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


    // Person beans
    @Bean
    public PersonService personService() {
        return new PersonServiceImpl();
    }

    @Bean
    public JdbcTemplatePersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }


    // Pad beans
    @Bean
    public PadService padService() {
        return new PadServiceImpl();
    }

    @Bean
    public JdbcTemplatePadDao jdbcPadDao() {
        return new JdbcTemplatePadDao();
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
                    .addScript("createNotepadServiceTestSchema.sql")
                    .build();
        }

        return db;
    }
}
