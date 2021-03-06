package com.epam.evernote.controller;

import com.epam.evernote.filter.CORSFilter;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.interfaces.TagService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TagControllerUnitTest extends ControllerUnitTest {

    {
        URL_BASE += "/1/tag";
        URL_CURRENT = URL_BASE + "/{id}";
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

    @Test
    public void test_cors_headers() throws Exception {
        super.test_cors_headers();
    }
}
