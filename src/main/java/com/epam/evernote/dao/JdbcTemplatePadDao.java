package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplatePadDao implements PadDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Pad pad) {
        String sql = "insert into Pad (id, person) values (?, ?)";
        return jdbcTemplate.update(sql, pad.getId(), pad.getPersonId());
    }

    @Override
    public Pad load(long id) {
        List<Pad> pads = jdbcTemplate.query("select * from Pad where id =?",
                new Object[]{id}, (resultSet, i) -> toPad(resultSet));

        if (pads.size() == 1) {
            return pads.get(0);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("delete from PERSON where id = ?", id);
    }

    @Override
    public void update(Pad pad) {

    }

    @Override
    public List<Pad> loadAll() {
        return jdbcTemplate.query("select * from Pad", (resultSet, i) -> toPad(resultSet));
    }

    private Pad toPad(ResultSet resultSet) throws SQLException {
        Pad pad = new Pad();
        pad.setId(resultSet.getLong("ID"));
        pad.setPersonId(resultSet.getLong("PERSON"));
        return pad;
    }


    @Override
    public Long getPadCount() {
        return jdbcTemplate.queryForObject("select count(*) from PAD",
                Long.class);
    }

    @Override
    public Long getPerson() {
        return null;
    }
}