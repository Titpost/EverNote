package com.epam.evernote.service.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.epam.evernote.dao.JdbcTemplatePadDao;
import com.epam.evernote.model.Pad;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PadServiceUnitTest {

    @Mock
    private JdbcTemplatePadDao padDao;

    @InjectMocks
    private PadServiceImpl padService;

    @Before
    public void setUp() {
    }

    @Test
    public void savePad() throws Exception {
        Pad pad = Pad.builder()
                .name("Name")
                .personId(1L)
                .build();
        padService.savePad(pad);
        verify(padDao, times(1)).save(pad);
        verifyNoMoreInteractions(padDao);
    }

    @Test
    public void getPadCount() throws Exception {
        Long padCount = 20L;
        when(padDao.getPadCount()).thenReturn(padCount);
        Long resultPadCount = padService.getPadCount();
        assertThat(resultPadCount).isEqualTo(padCount);

        verify(padDao, times(1)).getPadCount();
        verifyNoMoreInteractions(padDao);
    }

    @Test
    public void getAllPads() throws Exception {
        final long person = 1;
        Pad pad1 = Pad.builder()
                .name("Name1")
                .personId(person)
                .build();
        Pad pad2 = Pad.builder()
                .name("Name2")
                .personId(person)
                .build();
        List<Pad> pads = new ArrayList<>();
        pads.add(pad1);
        pads.add(pad2);

        when(padDao.loadAll(person)).thenReturn(pads);

        List<Pad> resultPad = padService.getAllPads(person);
        assertThat(resultPad).isEqualTo(pads);

        verify(padDao, times(1)).loadAll(person);
        verifyNoMoreInteractions(padDao);
    }

    @Test
    public void getPadById() throws Exception {
        long padId = 1;
        String padName = "Name";
        Pad pad = Pad.builder()
                .id(padId)
                .personId(1)
                .name(padName)
                .build();
        when(padDao.load(padId)).thenReturn(pad);
        Pad resultPad = padService.getPadById(padId);
        assertThat(resultPad).isEqualTo(pad);

        verify(padDao, times(1)).load(padId);
        verifyNoMoreInteractions(padDao);
    }

    @Test
    public void deletePad() throws Exception {
        Long padId = 1L;
        padService.deletePad(padId);
        verify(padDao, times(1)).delete(padId);
        verifyNoMoreInteractions(padDao);
    }
}
