package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.service.Implementations.PersonServiceImpl;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

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

    public EmbeddedDatabase embeddedDatabase() {
        return null;
    }
}
