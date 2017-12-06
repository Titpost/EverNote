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

  private Long id;
  private Long personId;
  private String name;
  private String body;
  private List<Note> notes;
}
