package com.epam.evernote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplatePersonDao implements PersonDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Person person) {
        String sql = "insert into Person (name, active) values (?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getActive());
    }

    @Override
    public Person load(long id) {
        List<Person> persons = jdbcTemplate.query("select * from Person where id =?",
                new Object[]{id}, (resultSet, i) -> {
                    return toPerson(resultSet);
                });

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
        return jdbcTemplate.query("select * from Person", (resultSet, i) -> {
            return toPerson(resultSet);
        });
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