package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.service.Implementations.NoteServiceImpl;
import com.epam.evernote.service.Interfaces.NoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Note Service testing.
 */
@Configuration
@ComponentScan(basePackages = {"com.epam.evernote.service.Implementations"})
public class NoteServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {


    // Note beans
    @Bean
    public NoteService noteService() {
        return new NoteServiceImpl();
    }

    @Bean
    public JdbcTemplateNoteDao jdbcNoteDao() {
        return new JdbcTemplateNoteDao();
    }

}
