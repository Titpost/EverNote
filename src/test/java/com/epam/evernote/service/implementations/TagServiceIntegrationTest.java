package com.epam.evernote.service.implementations;

import com.epam.evernote.config.TagServiceIntegrationTestConfig;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.interfaces.TagService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Integral test for Tag Service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TagServiceIntegrationTestConfig.class)
public class TagServiceIntegrationTest extends ServiceIntegrationTest {

    @Autowired
    private TagService tagService;

    /**
     * Database SetUp
     */
    @Before
    public void setUpDB() {
        assertNotNull(tagService);
        dataBase = db;
    }

    /**
     * Get tags count
     */
    @Test
    public void getAll() {
        final long note = 1;
        assertEquals(getTagsCount(note), tagService.getAllNoteTags(note).size());
    }

    /**
     * Create new tag
     */
    @Test
    public void createNew() {

        long prevCount = getCount();

        // create new tag with name1 for note1
        final int id1 = 1;
        Tag tag = Tag.builder().name(hardName).note(id1).build();
        tagService.saveTag(tag);

        // create new tag with name2 for note1
        tag = Tag.builder().name(hardName + "_2").note(id1).build();
        tagService.saveTag(tag);

        // create new tag with name1 for note2
        final int id2 = 2;
        tag = Tag.builder().name(hardName).note(id2).build();
        tagService.saveTag(tag);

        // create new tag with name2 for note2
        tag = Tag.builder().name(hardName +"_2").note(id2).build();
        tagService.saveTag(tag);

        // check row count
        assertEquals(prevCount + 4, getCount());
    }

    /**
     * Create 2 non unique tags
     */
    @Test
    public void createNotUniques() {

        long initialCount = getCount();

        // create new tag
        Tag tag = Tag.builder().name(hardName + "_nonUnique").note(1).build();
        tagService.saveTag(tag);

        // must be +1
        assertEquals(initialCount + 1, getCount());

        // create new tag with same name and note
        tagService.saveTag(tag);

        // must be + 1 (not + 2)
        assertEquals(initialCount + 1, getCount());
    }

    /**
     * Find note by its ID (name)
     */
    @Test
    public void findByOwnerAndName() {

        final String name = "Tag1";
        Tag tag = tagService.findTagByNameAndNote(name, 1);
        assertNotNull(tag);
        assertEquals(name, tag.getName());
    }

    /**
     * Try to find not existing note by wrong ID (name)
     */
    @Test
    public void findNotExisting() {

        // find tag by its name
        Tag tag = tagService.findTagByNameAndNote(hardName, 1);
        assertNull(tag);
    }

    /**
     * Delete tag
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create new tag
        final long note = 1;
        final String tagName = "toDelete";
        Tag tag = Tag.builder().name(tagName).note(note).build();
        tagService.saveTag(tag);
        assertNotNull(tagService.findTagByNameAndNote(tagName, note));
        final int count = tagService.getAllTags().size();

        // delete just created tag
        tagService.deleteTag(tagName, note);
        assertNull(tagService.findTagByNameAndNote(tagName, note));

        // check if table's row count decremented
        assertEquals(count - 1, tagService.getAllTags().size());
    }

    private long getCount() {
        return tagService.getTagCount();
    }

    private long getTagsCount(long pad) {
        return tagService.getNoteTagCount(pad);
    }
}