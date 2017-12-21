package com.epam.evernote.dao;

import com.epam.evernote.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class JdbcTemplateTagDao implements TagDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Tag tag) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", tag.getName());
        parameters.put("note", tag.getNote());

        try {
            new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("tag")
                    .execute(new MapSqlParameterSource(parameters));
        } catch (DuplicateKeyException e) {
            return 0;
        }
        return 1;
    }

    @Override
    public Tag load(String id) {
        List<Tag> tags = jdbcTemplate.query("SELECT * FROM tag WHERE name = ?",
                new Object[]{id}, (resultSet, i) -> toTag(resultSet));

        if (tags.size() == 1) {
            return tags.get(0);
        }
        return null;
    }

    public boolean exists(Tag tag) {
        List<Tag> tags = jdbcTemplate.query("SELECT * FROM tag WHERE " +
                        "id = ? AND note = ?",
                new Object[]{tag.getName(), getNote()}, (resultSet, i) -> toTag(resultSet));

        return (tags.size() > 0);
    }


    @Override
    public void delete(String name) {}

    @Override
    public void delete(String name, long note) {
        jdbcTemplate.update("DELETE FROM tag WHERE name = ? AND note = ?", name, note);
    }

    @Override
    public void update(Tag tag) {
    }

    @Override
    public List<Tag> loadAll() {
        return jdbcTemplate.query("SELECT * FROM tag", (resultSet, i) -> toTag(resultSet));
    }

    @Override
    public List<Tag> loadAll(long person) {
        return jdbcTemplate.query("SELECT * FROM tag " +
                        "JOIN note ON note.id = tag.note " +
                        "JOIN pad ON pad.id = note.pad AND pad.person = ? " +
                        "WHERE tag.name = ?",
                new Object[]{person}, (resultSet, i) -> toTag(resultSet));
    }

    private Tag toTag(ResultSet resultSet) throws SQLException {
        Tag tag = new Tag();
        tag.setName(resultSet.getString("name"));
        tag.setNote(resultSet.getLong("note"));
        return tag;
    }

    @Override
    public List<Tag> loadAllForNote(long note) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE tag.note = ?",
                new Object[]{note}, (resultSet, i) -> toTag(resultSet));
    }

    @Override
    public Tag findTagByNameAndNote(String name, long note) {
        List<Tag> tags = jdbcTemplate.query("SELECT * FROM tag WHERE name = ? AND note = ?",
                new Object[]{name, note}, (resultSet, i) -> toTag(resultSet));

        if (tags.size() == 1) {
            return tags.get(0);
        }

        return null;
    }

    @Override
    public long getTagCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tag",
                Long.class);
    }

    @Override
    public long getNoteTagCount(long note) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tag WHERE note = " + note,
                Long.class);
    }

    @Override
    public long getNote() {
        return -1;
    }
}