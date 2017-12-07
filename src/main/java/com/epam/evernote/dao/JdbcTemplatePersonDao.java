package com.epam.evernote.dao;

import com.epam.evernote.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("personTemplateRepo")
public class JdbcTemplatePersonDao implements PersonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Person person) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Person").usingGeneratedKeyColumns("Primary_key");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", person.getName());
        parameters.put("active", person.getActive());

        // execute insert
        return jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters)).intValue();
    }

    @Override
    public Person load(long id) {
        List<Person> persons = jdbcTemplate.query("select * from Person where id =?",
                new Object[]{id}, (resultSet, i) -> toPerson(resultSet));

        if (persons.size() == 1) {
            return persons.get(0);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from PERSON where id = ?", id);
    }

    @Override
    public void update(Person person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateName(long id, String newName) {
        jdbcTemplate.update("update PERSON set NAME = ? where ID = ?"
                , newName, id);
    }

    @Override
    public List<Person> loadAll() {
        return jdbcTemplate.query("select * from Person", (resultSet, i) -> toPerson(resultSet));
    }

    private Person toPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("ID"));
        person.setName(resultSet.getString("NAME"));
        return person;
    }

    @Override
    public List<Person> findPersonsByName(String name) {
        return jdbcTemplate.query("select * from Person where NAME = ?",
                new Object[]{name}, (resultSet, i) -> toPerson(resultSet));
    }

    @Override
    public Long getPersonCount() {
        return jdbcTemplate.queryForObject("select count(*) from PERSON",
                Long.class);
    }
}