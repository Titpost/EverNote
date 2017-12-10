package com.epam.evernote.service.Implementations;

import com.epam.evernote.config.TagServiceIntegrationTestConfig;
import com.epam.evernote.model.Tag;
import com.epam.evernote.service.Interfaces.TagService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("tagTemplateRepo")
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
        assertEquals((long)tagService.getTagCount(), tagService.getAllTags().size());
    }

    /**
     * Create new tag
     */
    @Test
    public void createNew() {

        final String tagName = "TagName";

        // create new tag
        Tag tag = Tag.builder().name(tagName).note("Note1").build();
        tagService.saveTag(tag);

        // check row count
        assertEquals(3, tagService.getAllTags().size());
        assertEquals(3, (long)tagService.getTagCount());
    }

    /**
     * Find note by its ID (name)
     */
    @Test
    public void findByName() {

        // create note with hard name
        Tag tag = Tag.builder().name(hardName).note("Note1").build();
        tagService.saveTag(tag);

        // find tag by its name
        tag = tagService.getTagById(hardName);
        assertNotNull(tag);
        assertEquals(hardName, tag.getName());

        // delete just created tag
        tagService.deleteTag(hardName);
    }

    /**
     * Try to find not existing note by wrong ID (name)
     */
    @Test
    public void findNotExisting() {

        // find tag by its name
        Tag tag = tagService.getTagById(hardName);
        assertNull(tag);
    }

    /**
     * Delete tag
     */
    @Test
    public void deleteByIdAndCheckCount() {

        // create new tag
        final String tagName = "toDelete";
        Tag tag = Tag.builder().name(tagName).note("Note1").build();
        tagService.saveTag(tag);
        assertNotNull(tagService.getTagById(tagName));
        final int count = tagService.getAllTags().size();

        // delete just created tag
        tagService.deleteTag(tagName);
        assertNull(tagService.getTagById(tagName));

        // check if table's row count decremented
        assertEquals(count - 1, tagService.getAllTags().size());
    }
}