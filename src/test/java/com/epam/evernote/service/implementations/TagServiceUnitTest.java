package com.epam.evernote.service.implementations;

import com.epam.evernote.dao.JdbcTemplateTagDao;
import com.epam.evernote.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceUnitTest {

    @Mock
    private JdbcTemplateTagDao tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Before
    public void setUp() {
    }

    @Test
    public void saveTag() throws Exception {
        Tag tag = Tag.builder()
                .name("Name")
                .note(1)
                .build();
        tagService.saveTag(tag);
        verify(tagDao, times(1)).save(tag);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void getNoteCount() throws Exception {
        Long tagCount = 20L;
        when(tagDao.getTagCount()).thenReturn(tagCount);
        Long resultTagCount = tagService.getTagCount();
        assertThat(resultTagCount).isEqualTo(tagCount);

        verify(tagDao, times(1)).getTagCount();
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void getAllNoteTags() throws Exception {
        final long noteId = 1;
        Tag tag1 = Tag.builder()
                .name("Name1")
                .note(noteId)
                .build();
        Tag tag2 = Tag.builder()
                .name("Name2")
                .note(noteId)
                .build();
        List<Tag> tag = new ArrayList<>();
        tag.add(tag1);
        tag.add(tag2);

        when(tagDao.loadAllForNote(noteId)).thenReturn(tag);

        List<Tag> resultTag = tagService.getAllNoteTags(1);
        assertThat(resultTag).isEqualTo(tag);

        verify(tagDao, times(1)).loadAllForNote(noteId);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void getTagByNameAndNote() throws Exception {
        final long noteId = 1;
        String tagName = "Tag1";
        Tag tag = Tag.builder()
                .name(tagName)
                .note(noteId)
                .build();
        when(tagDao.findTagByNameAndNote(tagName, noteId)).thenReturn(tag);
        Tag resultTag = tagService.findTagByNameAndNote(tagName, noteId);
        assertThat(resultTag).isEqualTo(tag);

        verify(tagDao, times(1)).findTagByNameAndNote(tagName, noteId);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void deleteTag() throws Exception {
        String tagName = "Name1";
        tagService.deleteTag(tagName, 1);
        verify(tagDao, times(1)).delete(tagName, 1);
        verifyNoMoreInteractions(tagDao);
    }
}