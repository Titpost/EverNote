package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.service.Interfaces.PersonService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Person Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class PersonServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {

    public PersonService personService;

    public JdbcTemplatePersonDao jdbcPersonDao;

}
