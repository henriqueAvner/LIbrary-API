package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.entity.BookDetail;

public record BookDetailCreationDto(
    String summary,
    Integer pageCount,
    String year,
    String isbn) {

  public BookDetail dtoToBookDetail(){
    return new BookDetail(summary, pageCount, year, isbn);
  }
}
