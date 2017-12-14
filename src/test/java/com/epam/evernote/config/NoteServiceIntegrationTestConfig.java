package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplateNoteDao;
import com.epam.evernote.service.Interfaces.NoteService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Note Service testing.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class NoteServiceIntegrationTestConfig extends ServiceIntegrationTestConfig {

    public NoteService noteService;

    public JdbcTemplateNoteDao jdbcNoteDao;
}
