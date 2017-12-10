package com.epam.evernote.dao;

import com.epam.evernote.model.Note;
import com.epam.evernote.model.Pad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Pad load(String id) {
        List<Pad> pads = jdbcTemplate.query("SELECT * FROM pad WHERE name =?",
                new Object[]{id}, (resultSet, i) -> toPad(resultSet));

        if (pads.size() == 1) {
            return pads.get(0);
        }
        return null;
    }

    @Override
    public Pad loadWithNotes(String id) {
        String sql = "select name, text, pad from note where pad='" + id + "'";
        return jdbcTemplate.query(sql, new PadWithNotesExtractor());
    }

    private class PadWithNotesExtractor implements ResultSetExtractor<Pad> {
        @Override
        public Pad extractData(ResultSet rs) throws SQLException {
            Pad pad = null;
            while (rs.next()) {
                if (pad == null) {
                    pad = new Pad();
                    pad.setName(rs.getString("name"));
                }
                Note note = new Note();
                note.setName(rs.getString("name"));
                note.setPadId(rs.getString("pad"));
                note.setText(rs.getString("text"));
                pad.addNote(note);
            }
            return pad;
        }
    }

    @Override
    public void delete(String id) {

        Pad pad = loadWithNotes(id);
        if (null != pad) {
            // delete tags
            for (Note note : pad.getNotes()) {
                jdbcTemplate.update("DELETE FROM tag WHERE note = ?", note.getName());
            }
            // delete notes
            jdbcTemplate.update("DELETE FROM note WHERE pad = ?", id);
        }
        // delete the pad
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