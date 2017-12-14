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

    private long id;
    private long padId;
    private String name;
    private String text;

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

}
