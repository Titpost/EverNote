package com.epam.evernote.config;


import com.epam.evernote.dao.JdbcTemplateTagDao;
import com.epam.evernote.service.Implementations.TagServiceImpl;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Tag Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class TagServiceIntegralTestConfig extends ServiceIntegralTestConfig {


    // Tag beans
    @Bean
    public TagService tagService() {
        return new TagServiceImpl();
    }

    @Bean
    public JdbcTemplateTagDao jdbcTagDao() {
        return new JdbcTemplateTagDao();
    }

}
