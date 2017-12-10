package com.epam.evernote.service.Implementations;

import org.junit.AfterClass;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;


public class ServiceIntegrationTest {

    protected static EmbeddedDatabase dataBase;

    protected final String hardName = "Some name rea11y hard to meet";


    /**
     * Closes DataBase connection
     */
    @AfterClass
    public static void tearDown() {
        dataBase.shutdown();
        dataBase = null;
    }
}
