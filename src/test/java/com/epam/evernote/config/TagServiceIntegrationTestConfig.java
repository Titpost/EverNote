package com.epam.evernote.config;


import com.epam.evernote.dao.JdbcTemplateTagDao;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Tag Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class TagServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {

    public TagService tagService;

    public JdbcTemplateTagDao jdbcTagDao;

}
