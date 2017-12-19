package com.epam.evernote.controller;


import com.epam.evernote.config.ApiControllerIntegrationTest;
import com.epam.evernote.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApiControllerIntegrationTest.class})
public class PersonControllerIntegrationTest {

    private static final String BASE_URI = "http://localhost:8080/person";
    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

    @Autowired
    private RestTemplate template;

    // =========================================== Get All the Persons ==========================================

    @Test
    public void test_get_all_success(){
        ResponseEntity<Person[]> response = template.getForEntity(BASE_URI, Person[].class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    // =========================================== Get Person By ID =========================================

    @Test
    public void test_get_by_id_success(){
        ResponseEntity<Person> response = template.getForEntity(BASE_URI + "/1", Person.class);
        Person person = response.getBody();
        assertThat(person.getId(), is(1L));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        validateCORSHttpHeaders(response.getHeaders());
    }

    @Test
    public void test_get_by_id_failure_not_found(){
        try {
            ResponseEntity<Person> response = template.getForEntity(BASE_URI + "/" + UNKNOWN_ID, Person.class);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Create New Person ========================================

    @Test
    public void test_create_new_user_success(){
        Person newPerson = Person.builder().id(777)
                .name("new username" + Math.random())
                .password("hashpassword")
                .active(true)
                .build();
        URI location = template.postForLocation(BASE_URI, newPerson, Person.class);
        assertThat(location, notNullValue());
    }

    @Test
    public void test_create_new_user_fail_exists(){
        Person existingPerson = Person.builder().id(1)
                .name("new username" + Math.random())
                .password("hashpassword")
                .active(true)
                .build();
        try {
            URI location = template.postForLocation(BASE_URI, existingPerson, Person.class);
            fail("should return 409 conflict");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.CONFLICT));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Update Existing Person ===================================

    @Test
    public void test_update_user_success(){
        Person existingPerson = Person.builder().id(3)
                .name("Name3")
                .password("hashpassword")
                .active(true)
                .build();
        template.put(BASE_URI + "/" + existingPerson.getId(), existingPerson);
    }

    @Test
    public void test_update_user_fail(){
        Person existingPerson = Person.builder().id(UNKNOWN_ID)
                .name("update")
                .password("hashpassword")
                .active(true)
                .build();
        try {
            template.put(BASE_URI + "/" + existingPerson.getId(), existingPerson);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    // =========================================== Delete Person ============================================

    @Test
    public void test_delete_user_success(){
        template.delete(BASE_URI + "/" + getLastPerson().getId());
    }

    @Test
    public void test_delete_user_fail(){
        try {
            template.delete(BASE_URI + "/" + UNKNOWN_ID);
            fail("should return 404 not found");
        } catch (HttpClientErrorException e){
            assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
            validateCORSHttpHeaders(e.getResponseHeaders());
        }
    }

    private Person getLastPerson(){
        ResponseEntity<Person[]> response = template.getForEntity(BASE_URI, Person[].class);
        Person[] users = response.getBody();
        return users[users.length - 1];
    }

    // =========================================== CORS Headers ===========================================

    public void validateCORSHttpHeaders(HttpHeaders headers){
        assertThat(headers.getAccessControlAllowOrigin(), is("*"));
        assertThat(headers.getAccessControlAllowHeaders(), hasItem("*"));
        assertThat(headers.getAccessControlMaxAge(), is(3600L));
        assertThat(headers.getAccessControlAllowMethods(), hasItems(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.OPTIONS,
                HttpMethod.DELETE));
    }
}
