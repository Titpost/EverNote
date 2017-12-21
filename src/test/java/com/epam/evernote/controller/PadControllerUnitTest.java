package com.epam.evernote.controller;

import com.epam.evernote.filter.CORSFilter;
import com.epam.evernote.model.Pad;
import com.epam.evernote.service.Interfaces.PadService;
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


public class PadControllerUnitTest extends ControllerUnitTest {

    {
        URL_BASE += "/1/pad";
        URL_CURRENT = URL_BASE + "/{id}";
    }

    @Mock
    private PadService padService;

    @InjectMocks
    private PadController padController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(padController)
                .addFilters(new CORSFilter())
                .build();
    }

    // =========================================== Get All Pads ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        final long personId = 1;
        Pad pad1 = Pad.builder().id(1)
                .name("Daenerys Targaryen")
                .personId(personId)
                .build();
        Pad pad2 = Pad.builder().id(2)
                .name("John Snow")
                .personId(personId)
                .build();
        List<Pad> pads = Arrays.asList(
                pad1,
                pad2);

        when(padService.getAllPads(personId)).thenReturn(pads);

        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("John Snow")))
        ;

        verify(padService, times(1)).getAllPads(personId);
        verifyNoMoreInteractions(padService);
    }

    // =========================================== Get Pad By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        Pad pad = Pad.builder().id(1)
                .name("Daenerys Targaryen")
                .personId(1)
                .build();

        when(padService.getPadById(1)).thenReturn(pad);

        mockMvc.perform(get(URL_CURRENT, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Daenerys Targaryen")))
        ;

        verify(padService, times(1)).getPadById(1);
        verifyNoMoreInteractions(padService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(padService.getPadById(9999)).thenReturn(null);

        mockMvc.perform(get(URL_CURRENT, 1))
                .andExpect(status().isNotFound());

        verify(padService, times(1)).getPadById(1L);
        verifyNoMoreInteractions(padService);
    }

    // =========================================== Create New Pad ========================================

    @Test
    public void test_create_pads_success() throws Exception {
        final long padId = 3;
        Pad pad = Pad.builder().id(padId)
                .name("Arya Stark")
                .personId(1)
                .build();

        when(padService.exists(pad)).thenReturn(false);

        mockMvc.perform(
                post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pad)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/person/1/pad")));

        verify(padService, times(1)).exists(pad);
        verify(padService, times(1)).savePad(pad);
        verifyNoMoreInteractions(padService);
    }

    @Test
    public void test_create_pad_fail_409_conflict() throws Exception {
        Pad pad = Pad.builder().id(3)
                .name("padname exists")
                .personId(1)
                .build();

        when(padService.exists(pad)).thenReturn(true);

        mockMvc.perform(
                post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pad)))
                .andExpect(status().isConflict());

        verify(padService, times(1)).exists(pad);
        verifyNoMoreInteractions(padService);
    }

    // =========================================== Update Existing Pad ===================================

    @Test
    public void test_update_pad_success() throws Exception {
        Pad pad = Pad.builder().id(3)
                .name("Arya Stark")
                .personId(1)
                .build();
        when(padService.getPadById(pad.getId())).thenReturn(pad);
        doNothing().when(padService).updateName(3, pad.getName());

        mockMvc.perform(
                put(URL_CURRENT, pad.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pad)))
                .andExpect(status().isOk());

        verify(padService, times(1)).getPadById(pad.getId());
        verify(padService, times(1)).updateName(1, pad.getName());
        verifyNoMoreInteractions(padService);
    }

    @Test
    public void test_update_pad_fail_404_not_found() throws Exception {
        Pad pad = Pad.builder().id(1)
                .name("pad not found")
                .personId(1)
                .build();

        when(padService.getPadById(pad.getId())).thenReturn(null);

        mockMvc.perform(
                put(URL_CURRENT, pad.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pad)))
                .andExpect(status().isNotFound());

        verify(padService, times(1)).getPadById(pad.getId());
        verifyNoMoreInteractions(padService);
    }

    // =========================================== Delete Pad ============================================

    @Test
    public void test_delete_pad_success() throws Exception {
        Pad pad = Pad.builder().id(3)
                .name("Arya Stark")
                .personId(1)
                .build();

        when(padService.getPadById(pad.getId())).thenReturn(pad);
        doNothing().when(padService).deletePad(pad.getId());

        mockMvc.perform(
                delete(URL_CURRENT, pad.getId()))
                .andExpect(status().isOk());

        verify(padService, times(1)).getPadById(pad.getId());
        verify(padService, times(1)).deletePad(pad.getId());
        verifyNoMoreInteractions(padService);
    }

    @Test
    public void test_delete_pad_fail_404_not_found() throws Exception {
        Pad pad = Pad.builder().id(1)
                .name("pad not found")
                .personId(1)
                .build();

        when(padService.getPadById(pad.getId())).thenReturn(null);

        mockMvc.perform(
                delete(URL_CURRENT, pad.getId()))
                .andExpect(status().isNotFound());

        verify(padService, times(1)).getPadById(pad.getId());
        verifyNoMoreInteractions(padService);
    }

    @Test
    public void test_cors_headers() throws Exception {
        super.test_cors_headers();
    }
}
