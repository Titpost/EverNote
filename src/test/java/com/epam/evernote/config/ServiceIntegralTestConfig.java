package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.service.Implementations.PersonServiceImpl;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class ServiceIntegralTestConfig {


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


    protected static EmbeddedDatabase db = null;

    @Lazy
    @Bean
    public EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("createPersonServiceTestSchema.sql")
                .build();
    }
}
