package com.epam.evernote.dao;

import com.epam.evernote.model.Pad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository("padTemplateRepo")
public class JdbcTemplatePadDao implements PadDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Pad pad) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", pad.getName());
        parameters.put("person", pad.getPersonId());

        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("pad")
                .execute(new MapSqlParameterSource(parameters));

        return 0;
    }

    @Override
    public Pad load(long id) {
        List<Pad> pads = jdbcTemplate.query("SELECT * FROM зad WHERE id =?",
                new Object[]{id}, (resultSet, i) -> toPad(resultSet));

        if (pads.size() == 1) {
            return pads.get(0);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM pad WHERE name = ?", id);
    }

    @Override
    public void update(Pad pad) {

    }

    @Override
    public List<Pad> loadAll() {
        return jdbcTemplate.query("SELECT * FROM pad", (resultSet, i) -> toPad(resultSet));
    }

    private Pad toPad(ResultSet resultSet) throws SQLException {
        Pad pad = new Pad();
        pad.setName(resultSet.getString("name"));
        pad.setPersonId(resultSet.getLong("person"));
        return pad;
    }


    @Override
    public Long getPadCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pad",
                Long.class);
    }

    @Override
    public Long getPerson() {
        return null;
    }
}