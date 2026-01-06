package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.entity.Author;

public record AuthorCreationDto(String name, String nationality) {

  //Transformando o Dto em entidade
  public Author toEntity(){
    return new Author(name, nationality);
  }
}
