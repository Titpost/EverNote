package com.epam.evernote.config;

import com.epam.evernote.dao.JdbcTemplatePersonDao;
import com.epam.evernote.service.Interfaces.NoteService;
import com.epam.evernote.service.Interfaces.PadService;
import com.epam.evernote.service.Interfaces.PersonService;
import com.epam.evernote.service.Interfaces.TagService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.epam.evernote.service.Implementations",
        "com.epam.evernote.dao"
})
public class ServiceConfig extends DataBaseConfig {

    public PersonService personService;
    public PadService padService;
    public NoteService noteService;
    public TagService tagService;

    public JdbcTemplatePersonDao jdbcPersonDao;
}
