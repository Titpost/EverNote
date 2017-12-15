package com.epam.evernote.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pad {

    private long id;
    private long personId;
    private String name;

    public void addNote(Note note) {
        notes.add(note);
    }

    //@Builder.Default
    private List<Note> notes = new ArrayList<>();
}