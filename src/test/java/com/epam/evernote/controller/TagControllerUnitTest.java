package com.epam.evernote.controller;

import com.epam.evernote.filter.CORSFilter;
import com.epam.evernote.model.Note;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.TagService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TagControllerUnitTest extends ControllerUnitTest {

    final String URL_WITH_NOTE_BASE;
    {
        URL_WITH_NOTE_BASE = URL_BASE + "/{person}/pad/{pad}/note/{note}/tag";
        URL_CURRENT = URL_WITH_NOTE_BASE + "/{tag}";
        URL_BASE += "/1/tag";
    }

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(tagController)
                .addFilters(new CORSFilter())
                .build();
    }

    // =========================================== Get All Tags ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        final long noteId = 1;
        Tag tag1 = Tag.builder().name("1")
                .name("DaenerysTargaryen")
                .note(noteId)
                .build();
        Tag tag2 = Tag.builder().name("2")
                .name("JohnSnow")
                .note(noteId)
                .build();
        List<Tag> tags = Arrays.asList(
                tag1,
                tag2);

        when(tagService.getAllTags(noteId)).thenReturn(tags);

        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("DaenerysTargaryen")))
                .andExpect(jsonPath("$[1].name", is("JohnSnow")));

        verify(tagService, times(1)).getAllTags(noteId);
        verifyNoMoreInteractions(tagService);
    }

    // =========================================== Get Tag By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        final String name = "Daenerys Targaryen";
        final long note = 1;
        Tag tag = Tag.builder().name(name)
                .name(name)
                .note(note)
                .build();

        when(tagService.findTagByNameAndNote(name, note)).thenReturn(tag);

        mockMvc.perform(get(URL_CURRENT, 1, 1, note, name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.note", is((int)note)))
        ;

        verify(tagService, times(1)).findTagByNameAndNote(name, note);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void test_cors_headers() throws Exception {
        super.test_cors_headers();
    }
}
