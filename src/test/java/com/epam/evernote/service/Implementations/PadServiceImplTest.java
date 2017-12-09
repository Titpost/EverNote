package com.epam.evernote.service.Implementations;

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
public class PadServiceImplTest {

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
    Long resultPudCount = padService.getPadCount();
    assertThat(resultPudCount).isEqualTo(padCount);

    verify(padDao, times(1)).getPadCount();
    verifyNoMoreInteractions(padDao);
  }

  @Test
  public void getAllPads() throws Exception {
    Pad pad1 = Pad.builder()
        .name("Name1")
        .personId(1L)
        .build();
    Pad pad2 = Pad.builder()
        .name("Name2")
        .personId(1L)
        .build();
    List<Pad> pad = new ArrayList<>();
    pad.add(pad1);
    pad.add(pad2);

    when(padDao.loadAll()).thenReturn(pad);

    List<Pad> resultPad = padService.getAllPads();
    assertThat(resultPad).isEqualTo(pad);

    verify(padDao, times(1)).loadAll();
    verifyNoMoreInteractions(padDao);
  }

  @Test
  public void getPadById() throws Exception {
    Pad pad = Pad.builder()
         .name("Name")
        .personId(1L)
        .build();
    Long padId = 1L;
    when(padDao.load(padId)).thenReturn(pad);
    Pad resultPad = padService.getPadById(padId);
    assertThat(resultPad).isEqualTo(pad);

    verify(padDao, times(1)).load(padId);
    verifyNoMoreInteractions(padDao);
  }

  @Test
  public void deletePad() throws Exception {
    Long id = 1L;
    padService.deletePad(id);
    verify(padDao, times(1)).delete(id);
    verifyNoMoreInteractions(padDao);
  }

}