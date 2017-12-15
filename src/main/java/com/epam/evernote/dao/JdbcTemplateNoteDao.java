package com.epam.evernote.dao;

import com.epam.evernote.model.Note;
import com.epam.evernote.model.Pad;
import com.epam.evernote.model.Tag;
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
public class JdbcTemplateNoteDao implements NoteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Note note) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("pad", note.getPadId());
        parameters.put("name", note.getName());
        parameters.put("text", note.getText());

        // execute insert
        try {
            return new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("note")
                    .usingGeneratedKeyColumns("Primary_key")
                    .executeAndReturnKey(new MapSqlParameterSource(
                            parameters)).intValue();
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Note load(Long id) {
        List<Note> notes = jdbcTemplate.query("SELECT * FROM note WHERE id = ?",
                new Object[]{id}, (resultSet, i) -> toNote(resultSet));

        if (notes.size() == 1) {
            return notes.get(0);
        }
        return null;
    }

    @Override
    public Note loadWithTags(long id) {
        String sql = "select name, note from tag where note = '" + id + "'";
        return jdbcTemplate.query(sql, new JdbcTemplateNoteDao.NoteWithTagsExtractor());
    }

    private class NoteWithTagsExtractor implements ResultSetExtractor<Note> {
        @Override
        public Note extractData(ResultSet rs) throws SQLException {
            Note note = null;
            while (rs.next()) {
                if (note == null) {
                    note = new Note();
                    note.setName(rs.getString("name"));
                }
                Tag tag = new Tag();
                tag.setName(rs.getString("name"));
                tag.setNote(rs.getLong("note"));
                note.addTag(tag);
            }
            return note;
        }
    }

    @Override
    public void delete(Long id) {

        // delete tags
        jdbcTemplate.update("DELETE FROM tag WHERE note = ?", id);

        // delete the note
        jdbcTemplate.update("DELETE FROM note WHERE id = ?", id);
    }

    @Override
    public void update(Note note) {
    }

    @Override
    public List<Note> loadAll() {
        return jdbcTemplate.query("SELECT * FROM note", (resultSet, i) -> toNote(resultSet));
    }

    private Note toNote(ResultSet resultSet) throws SQLException {
        Note note = new Note();
        note.setId(resultSet.getLong("id"));
        note.setPadId(resultSet.getLong("pad"));
        note.setName(resultSet.getString("name"));
        return note;
    }

    @Override
    public Note findNoteByOwnerAndName(long person, String name) {
        List<Note> notes = jdbcTemplate.query(//"SELECT * FROM note WHERE person = ? AND name = ?",
                "SELECT * FROM note " +
                "JOIN pad ON pad.id = note.pad AND pad.person = ? WHERE note.name = ?",
                new Object[]{person, name}, (resultSet, i) -> toNote(resultSet));

        if (notes.size() == 1) {
            return notes.get(0);
        }
        return null;
    }


    @Override
    public long getNoteCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM note",
                Long.class);
    }

    @Override
    public long getPad() {
        return 0;
    }
}