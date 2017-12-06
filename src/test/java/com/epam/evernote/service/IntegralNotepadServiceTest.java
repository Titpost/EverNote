package com.epam.evernote.service;

import com.epam.evernote.model.Pad;
import com.epam.evernote.model.Person;
import com.epam.evernote.config.IntegralPadServiceConfig;
import com.epam.evernote.service.Implementations.PadServiceImpl;
import com.epam.evernote.service.Implementations.PersonServiceImpl;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Integral test for Notepad Service.
 */
public class IntegralNotepadServiceTest {

  private static AnnotationConfigApplicationContext padTestContext;
  private static PersonServiceImpl personServiceImpl;
  private static PadServiceImpl padServiceImpl;

  /**
   * Database SetUp
   */
  @BeforeClass
  public static void setUpDB() {
    padTestContext = new AnnotationConfigApplicationContext(IntegralPadServiceConfig.class);
    personServiceImpl = padTestContext.getBean(PersonServiceImpl.class);
    padServiceImpl = padTestContext.getBean(PadServiceImpl.class);
  }

  /**
   * Test with series of operations within Notepad
   */
  @Test
  public void TestNotepadService() {

    final String Name1 = "Name 1";
    final String Name2 = "Name 2";

    // create one person with hard name
    Person person1 = Person.builder().name("Name1").active(true).build();
    personServiceImpl.savePerson(person1);

    // create another person with easy name
    Person person2 = Person.builder().name("Name2").active(true).build();
    personServiceImpl.savePerson(person2);

    // create new notepad
    Pad pad = Pad.builder().id(1L).name("Name 1").build();
    padServiceImpl.savePad(pad);
    Assert.assertEquals(padServiceImpl.getAllPads().size(), 1);
  }

  /**
   * Closes DataBase connection
   *
   * @throws SQLException if something went wrong with DB connection
   */
  @AfterClass
  public static void tearDown() throws SQLException {
    padTestContext.getBean(DataSource.class).getConnection().close();
  }
}