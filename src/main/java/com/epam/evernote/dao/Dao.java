package com.epam.evernote.dao;


import java.util.List;

public interface Dao<T> {

    int save(T t);

    T load(long id);

    void delete(long id);

    void update(T t);

    List<T> loadAll();

}