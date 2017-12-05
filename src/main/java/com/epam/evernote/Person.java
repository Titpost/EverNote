package com.epam.evernote;

public class Person {
    private long id;
    private String name;
    private Boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }

    public static Person create(String name,  Boolean active) {
        Person person = new Person();
        person.setName(name);
        person.setActive(active);
        return person;
    }
}