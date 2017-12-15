package com.epam.evernote.service.Implementations;

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
    public void getAllTags() throws Exception {
        Tag tag1 = Tag.builder()
                .name("Name1")
                .note(1)
                .build();
        Tag tag2 = Tag.builder()
                .name("Name2")
                .note(1)
                .build();
        List<Tag> tag = new ArrayList<>();
        tag.add(tag1);
        tag.add(tag2);

        when(tagDao.loadAll()).thenReturn(tag);

        List<Tag> resultTag = tagService.getAllTags();
        assertThat(resultTag).isEqualTo(tag);

        verify(tagDao, times(1)).loadAll();
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void getTagById() throws Exception {
        String tagId = "Name";
        Tag tag = Tag.builder()
                .name(tagId)
                .note(1)
                .build();
        when(tagDao.load(tagId)).thenReturn(tag);
        Tag resultTag = tagService.getTagById(tagId);
        assertThat(resultTag).isEqualTo(tag);

        verify(tagDao, times(1)).load(tagId);
        verifyNoMoreInteractions(tagDao);
    }

    @Test
    public void deleteTag() throws Exception {
        String tagId = "Name";
        tagService.deleteTag(tagId);
        verify(tagDao, times(1)).delete(tagId);
        verifyNoMoreInteractions(tagDao);
    }
}