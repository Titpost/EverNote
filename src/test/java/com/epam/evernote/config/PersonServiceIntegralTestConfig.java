package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.service.Implementations.PersonServiceImpl;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Person Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class PersonServiceIntegralTestConfig extends ServiceIntegralTestConfig {

    // Person beans
    @Bean
    public PersonService personService() {
        return new PersonServiceImpl();
    }

    @Bean
    public JdbcTemplatePersonDao jdbcPersonDao() {
        return new JdbcTemplatePersonDao();
    }


}
