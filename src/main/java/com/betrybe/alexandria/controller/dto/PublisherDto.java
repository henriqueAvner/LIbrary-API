package com.betrybe.alexandria.controller.dto;

import com.betrybe.alexandria.entity.Publisher;

public record PublisherDto(Long id, String nome, String address) {

  public static PublisherDto fromEntity(Publisher publisher) {
    return new PublisherDto(
        publisher.getId(),
        publisher.getNome(),
        publisher.getAddress()
    );
  }
}
