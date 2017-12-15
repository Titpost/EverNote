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

    @Override
    public void delete(String id) {
        jdbcTemplate.update("DELETE FROM tag WHERE name = ?", id);
    }

    @Override
    public void update(Tag tag) {
    }

    @Override
    public List<Tag> loadAll() {
        return jdbcTemplate.query("SELECT * FROM tag", (resultSet, i) -> toTag(resultSet));
    }

    private Tag toTag(ResultSet resultSet) throws SQLException {
        Tag tag = new Tag();
        tag.setName(resultSet.getString("name"));
        tag.setNote(resultSet.getLong("note"));
        return tag;
    }


    @Override
    public Long getTagCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tag",
                Long.class);
    }

    @Override
    public String getNote() {
        return null;
    }
}