package com.epam.evernote.model;


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
  private List<Tag> notes;
}
