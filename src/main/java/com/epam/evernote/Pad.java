package com.epam.evernote;

public class Pad {
    private String id;
    private long person;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPerson() {
        return person;
    }

    public void setPerson(long person) {
        this.person = person;
    }


    @Override
    public String toString() {
        return "Notepad{" +
                "name=" + id +
                " of user with id=" + person +
                '}';
    }

    public static Pad create(String name, long person) {
        Pad pad = new Pad();
        pad.setId(name);
        pad.setPerson(person);
        return pad;
    }
}