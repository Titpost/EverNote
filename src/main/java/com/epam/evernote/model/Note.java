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
public class Note {

    private String name;
    private String padId;
    private String text;
    private List<Tag> tags = new ArrayList<>();
    public void addTag(Tag tag) {
        tags.add(tag);
    }
}
