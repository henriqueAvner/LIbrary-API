package com.betrybe.alexandria.service;

import com.betrybe.alexandria.entity.Publisher;
import com.betrybe.alexandria.repository.PublisherRepository;
import com.betrybe.alexandria.service.exception.PublisherNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

  private final PublisherRepository publisherRepository;

  @Autowired
  public PublisherService(PublisherRepository publisherRepository) {
    this.publisherRepository = publisherRepository;
  }

  public List<Publisher> findAll() {
    return publisherRepository.findAll();
  }

  public Publisher findById(Long id) throws PublisherNotFoundException {
    return publisherRepository.findById(id)
        .orElseThrow(PublisherNotFoundException::new);
  }

  public Publisher create(Publisher publisher) {
    return publisherRepository.save(publisher);
  }

  public Publisher update(Long id, Publisher publisher) throws PublisherNotFoundException {

    Publisher publisherToUpdate = findById(id);
    publisherToUpdate.setNome(publisher.getNome());
    publisherToUpdate.setAddress(publisher.getAddress());

    publisherRepository.save(publisherToUpdate);

    return publisherToUpdate;
  }

  public Publisher delete(Long id) throws PublisherNotFoundException {
    Publisher publihserForDelete = findById(id);

    publisherRepository.delete(publihserForDelete);

    return publihserForDelete;
  }

}
















