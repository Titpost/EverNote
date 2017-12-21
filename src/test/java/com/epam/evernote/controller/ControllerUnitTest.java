package com.epam.evernote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


public class ControllerUnitTest {

    String URL_BASE = "/api/person";
    String URL_CURRENT;

    MockMvc mockMvc;

    String getBaseUrl() {
        return this.URL_BASE;
    }

    // =========================================== CORS Headers ===========================================

    public void test_cors_headers() throws Exception {
        mockMvc.perform(get(getBaseUrl()))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().string("Access-Control-Max-Age", "3600"));
    }

    /*
     * converts a Java object into JSON representation
     */
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
