package com.epam.evernote.dao;

import com.epam.evernote.model.Note;
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

@Repository("noteTemplateRepo")
public class JdbcTemplateNoteDao implements NoteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Note note) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", note.getName());
        parameters.put("person", note.getPadId());

        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("note")
                .execute(new MapSqlParameterSource(parameters));

        return 0;
    }

    @Override
    public Note load(String id) {
        List<Note> notes = jdbcTemplate.query("SELECT * FROM note WHERE name =?",
                new Object[]{id}, (resultSet, i) -> toNote(resultSet));

        if (notes.size() == 1) {
            return notes.get(0);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM note WHERE name = ?", id);
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
        note.setName(resultSet.getString("name"));
        note.setPadId(resultSet.getString("pad"));
        return note;
    }


    @Override
    public Long getNoteCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM note",
                Long.class);
    }

    @Override
    public String getPad() {
        return null;
    }
}