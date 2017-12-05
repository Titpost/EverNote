package com.epam.evernote;

import java.util.List;

public interface PersonDao extends Dao<Person>{

    List<Person> findPersonsByName(String name);

    Long getPersonCount();

    void updateName(long id, String newName);
}
