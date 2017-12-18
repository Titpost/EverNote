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

@Repository
public class JdbcTemplatePersonDao implements PersonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Person person) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", person.getName());
        parameters.put("active", person.getActive());

        // execute insert
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("person")
                .usingGeneratedKeyColumns("Primary_key")
                .executeAndReturnKey(new MapSqlParameterSource(
                        parameters)).intValue();
    }

    @Override
    public Person load(Long id) {
        List<Person> persons = jdbcTemplate.query("SELECT * FROM person WHERE id =?",
                new Object[]{id}, (resultSet, i) -> toPerson(resultSet));

        if (persons.size() == 1) {
            return persons.get(0);
        }
        return null;
    }

    public boolean exists(Person person) {
        List<Person> persons = jdbcTemplate.query("SELECT * FROM person WHERE " +
                        "id = ?, name = ?",
                new Object[]{person.getId(), person.getName()}, (resultSet, i) -> toPerson(resultSet));

        return (persons.size() > 0);
    }


    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE from PERSON where id = ?", id);
    }

    @Override
    public void update(Person person) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateName(long id, String newName) {
        jdbcTemplate.update("UPDATE PERSON SET name = ? WHERE id = ?"
                , newName, id);
    }

    @Override
    public List<Person> loadAll() {
        return jdbcTemplate.query("SELECT * FROM person", (resultSet, i) -> toPerson(resultSet));
    }

    private Person toPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setName(resultSet.getString("name"));
        return person;
    }

    @Override
    public List<Person> findPersonsByName(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?",
                new Object[]{name}, (resultSet, i) -> toPerson(resultSet));
    }

    @Override
    public long getPersonCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM person",
                Long.class);
    }
}