package com.epam.evernote.controller;

import com.epam.evernote.filter.CORSFilter;
import com.epam.evernote.model.Person;
import com.epam.evernote.service.Interfaces.PersonService;
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


public class PersonControllerUnitTest extends ControllerUnitTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .addFilters(new CORSFilter())
                .build();
    }

    // =========================================== Get All Persons ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        Person person1 = Person.builder().id(1)
                .name("Daenerys Targaryen")
                .password("hashpassword")
                .active(true)
                .build();
        Person person2 = Person.builder().id(2)
                .name("John Snow")
                .password("hashpassword")
                .active(true)
                .build();
        List<Person> persons = Arrays.asList(
                person1,
                person2);

        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("John Snow")))
        ;

        verify(personService, times(1)).getAllPersons();
        verifyNoMoreInteractions(personService);
    }

    // =========================================== Get Person By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        Person person = Person.builder().id(1)
                .name("Daenerys Targaryen")
                .password("hashpassword")
                .active(true)
                .build();

        when(personService.getPersonById(1)).thenReturn(person);

        mockMvc.perform(get(URL_BASE + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Daenerys Targaryen")))
        ;

        verify(personService, times(1)).getPersonById(1);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(personService.getPersonById(9999)).thenReturn(null);

        mockMvc.perform(get(URL_BASE + "/{id}", 1))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).getPersonById(1);
        verifyNoMoreInteractions(personService);
    }

    // =========================================== Create New Person ========================================

    @Test
    public void test_create_persons_success() throws Exception {
        Person person = Person.builder().id(1)
                .name("Arya Stark")
                .password("hashpassword")
                .active(true)
                .build();

        when(personService.exists(person)).thenReturn(false);

        mockMvc.perform(
                post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(person)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("http://localhost/person/1")));

        verify(personService, times(1)).exists(person);
        verify(personService, times(1)).savePerson(person);
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void test_create_person_fail_409_conflict() throws Exception {
        Person person = Person.builder().id(1)
                .name("personname exists")
                .password("hashpassword")
                .active(true)
                .build();

        when(personService.exists(person)).thenReturn(true);

        mockMvc.perform(
                post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(person)))
                .andExpect(status().isConflict());

        verify(personService, times(1)).exists(person);
        verifyNoMoreInteractions(personService);
    }

    // =========================================== Update Existing Person ===================================

    @Test
    public void test_update_person_success() throws Exception {
        Person person = Person.builder().id(1)
                .name("Arya Stark")
                .password("hashpassword")
                .active(true)
                .build();
        when(personService.getPersonById(person.getId())).thenReturn(person);
        doNothing().when(personService).updateName(1, person.getName());

        mockMvc.perform(
                put(URL_BASE + "/{id}", person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(person)))
                .andExpect(status().isOk());

        verify(personService, times(1)).getPersonById(person.getId());
        verify(personService, times(1)).updateName(1, person.getName());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void test_update_person_fail_404_not_found() throws Exception {
        Person person = Person.builder().id(1)
                .name("person not found")
                .password("hashpassword")
                .active(true)
                .build();

        when(personService.getPersonById(person.getId())).thenReturn(null);

        mockMvc.perform(
                put(URL_BASE + "/{id}", person.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(person)))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).getPersonById(person.getId());
        verifyNoMoreInteractions(personService);
    }

    // =========================================== Delete Person ============================================

    @Test
    public void test_delete_person_success() throws Exception {
        Person person = Person.builder().id(1)
                .name("Arya Stark")
                .password("hashpassword")
                .active(true)
                .build();

        when(personService.getPersonById(person.getId())).thenReturn(person);
        doNothing().when(personService).deletePerson(person.getId());

        mockMvc.perform(
                delete(URL_BASE + "/{id}", person.getId()))
                .andExpect(status().isOk());

        verify(personService, times(1)).getPersonById(person.getId());
        verify(personService, times(1)).deletePerson(person.getId());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void test_delete_person_fail_404_not_found() throws Exception {
        Person person = Person.builder().id(1)
                .name("person not found")
                .password("hashpassword")
                .active(true)
                .build();

        when(personService.getPersonById(person.getId())).thenReturn(null);

        mockMvc.perform(
                delete(URL_BASE + "/{id}", person.getId()))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).getPersonById(person.getId());
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void test_cors_headers() throws Exception {
        super.test_cors_headers();
    }
}
