package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.entity.Author;

public record AuthorDto(Long id, String name, String nationality) {

  //Transformando o Dto em entidade
  public static AuthorDto fromEntity (Author author) {
    return new AuthorDto(
        author.getId(),
        author.getName(),
        author.getNationality()
    );
  }
}
