package com.betrybe.alexandria.controller.dto;
import com.betrybe.alexandria.entity.Publisher;

public record PublisherCreationDto(String nome, String address) {

  public Publisher toEntity() {
    return new Publisher(nome, address);
  }
}
