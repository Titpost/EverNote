package com.epam.evernote.dao;


import java.util.List;

public interface Dao<T, K> {

    long save(T t);

    T load(K id);

    void delete(K id);

    void update(T t);

    List<T> loadAll();

}