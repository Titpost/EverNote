package com.epam.evernote.dao;

import com.epam.evernote.model.Note;
import com.epam.evernote.model.Pad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

@Repository
public class JdbcTemplatePadDao implements PadDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Pad pad) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("person", pad.getPersonId());
        parameters.put("name", pad.getName());

        // execute insert
        try {
            return new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("pad")
                    .usingGeneratedKeyColumns("Primary_key")
                    .executeAndReturnKey(new MapSqlParameterSource(
                            parameters)).intValue();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Pad load(Long id) {
        List<Pad> pads = jdbcTemplate.query("SELECT * FROM pad WHERE id = ?",
                new Object[]{id}, (resultSet, i) -> toPad(resultSet));

        if (pads.size() == 1) {
            return pads.get(0);
        }
        return null;
    }

    @Override
    public Pad loadWithNotes(long id) {
        String sql = "select id, name, text from note where pad = '" + id + "'";
        return jdbcTemplate.query(sql, new PadWithNotesExtractor(id));
    }

    private class PadWithNotesExtractor implements ResultSetExtractor<Pad> {
        private long id;
        private PadWithNotesExtractor(long id) {
            this.id = id;
        }
        @Override
        public Pad extractData(ResultSet rs) throws SQLException {
            Pad pad = null;
            while (rs.next()) {
                if (pad == null) {
                    pad = new Pad();
                    pad.setId(id);
                }
                Note note = new Note();
                note.setId(rs.getLong("id"));
                note.setPadId(id);
                note.setName(rs.getString("name"));
                note.setText(rs.getString("text"));
                pad.addNote(note);
            }
            return pad;
        }
    }

    public boolean exists(Pad pad) {
        List<Pad> pads = jdbcTemplate.query("SELECT * FROM pad WHERE " +
                        "id = ?",
                new Object[]{pad.getId()}, (resultSet, i) -> toPad(resultSet));

        return (pads.size() > 0);
    }

    @Override
    public void delete(Long id) {

        Pad pad = loadWithNotes(id);
        if (null != pad) {
            // delete tags
            for (Note note : pad.getNotes()) {
                jdbcTemplate.update("DELETE FROM tag WHERE note = ?", note.getId());
            }
            // delete notes
            jdbcTemplate.update("DELETE FROM note WHERE pad = ?", id);
        }
        // delete the pad
        jdbcTemplate.update("DELETE FROM pad WHERE id = ?", id);
    }

    @Override
    public void update(Pad pad) {
    }

    @Override
    public void updateName(long id, String newName) {
        jdbcTemplate.update("UPDATE pad SET name = ? WHERE id = ?"
                , newName, id);
    }

    @Override
    public List<Pad> loadAll() {
        return jdbcTemplate.query("SELECT * FROM pad", (resultSet, i) -> toPad(resultSet));
    }

    @Override
    public List<Pad> loadAll(long person) {
        return jdbcTemplate.query("SELECT * FROM pad WHERE person = ?",
                new Object[]{person}, (resultSet, i) -> toPad(resultSet));
    }

    private Pad toPad(ResultSet resultSet) throws SQLException {
        Pad pad = new Pad();
        pad.setId(resultSet.getLong("id"));
        pad.setName(resultSet.getString("name"));
        pad.setPersonId(resultSet.getLong("person"));
        return pad;
    }

    @Override
    public Pad findPadByIdAndOwner(long id, long person) {
        List<Pad> pads = jdbcTemplate.query("SELECT * FROM pad WHERE id = ? AND person = ?",
                new Object[]{id, person}, (resultSet, i) -> toPad(resultSet));

        if (pads.size() == 1) {
            return pads.get(0);
        }
        return null;
    }

    @Override
    public long getPadCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pad",
                Long.class);
    }

    @Override
    public long getPerson() {
        return 0;
    }
}