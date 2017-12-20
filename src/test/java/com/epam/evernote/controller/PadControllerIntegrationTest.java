package com.epam.evernote.controller;


import com.epam.evernote.config.ApiControllerIntegrationTest;
import com.epam.evernote.model.Pad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApiControllerIntegrationTest.class})
public class PadControllerIntegrationTest extends ControllerIntegrationTest {

    {
        BASE_URI += "/1/pad";
    }

    // =========================================== Get All the Pads ==========================================

    @Test
    public void test_get_all_success(){
        ResponseEntity<Pad[]> response = template.getForEntity(BASE_URI, Pad[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    // =========================================== Get Pad By ID =========================================

    @Test
    public void test_get_by_id_success(){
        ResponseEntity<Pad> response = template.getForEntity(BASE_URI + "/1", Pad.class);
        Pad pad = response.getBody();
        assertThat(pad.getId(), is(1L));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_by_id_failure_not_found(){
        try {
            ResponseEntity<Pad> response = template.getForEntity(BASE_URI + "/" + UNKNOWN_ID, Pad.class);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Create New Pad ========================================

    @Test
    public void test_create_new_pad_success(){
        Pad newPad = Pad.builder().id(777)
                .name("new notepad" + Math.random())
                .personId(1)
                .build();
        URI location = template.postForLocation(BASE_URI, newPad, Pad.class);
        assertThat(location, notNullValue());
    }

    @Test
    public void test_create_new_pad_fail_exists(){
        Pad existingPad = Pad.builder().id(1)
                .name("new notepad" + Math.random())
                .personId(1)
                .build();
        try {
            template.postForLocation(BASE_URI, existingPad, Pad.class);
            fail("should return 409 conflict");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.CONFLICT));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Update Existing Pad ===================================

    @Test
    public void test_update_pad_success(){
        Pad existingPad = Pad.builder().id(2)
                .name("PadName3")
                .personId(1)
                .build();
        template.put(BASE_URI + "/" + existingPad.getId(), existingPad);
    }

    @Test
    public void test_update_pad_fail(){
        Pad existingPad = Pad.builder().id(UNKNOWN_ID)
                .name("update")
                .personId(1)
                .build();
        try {
            template.put(BASE_URI + "/" + existingPad.getId(), existingPad);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Delete Pad ============================================

    @Test
    public void test_delete_pad_success(){
        template.delete(BASE_URI + "/" + getLastPad().getId());
    }

    @Test
    public void test_delete_pad_fail(){
        try {
            template.delete(BASE_URI + "/" + UNKNOWN_ID);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    private Pad getLastPad(){
        ResponseEntity<Pad[]> response = template.getForEntity(BASE_URI, Pad[].class);
        Pad[] pads = response.getBody();
        return pads[pads.length - 1];
    }
}
