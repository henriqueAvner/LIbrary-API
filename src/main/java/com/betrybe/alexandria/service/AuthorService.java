package com.betrybe.alexandria.service;

import com.betrybe.alexandria.entity.Author;
import com.betrybe.alexandria.repository.AuthorRepository;
import com.betrybe.alexandria.service.exception.AuthorNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

  public final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public List<Author> findAll(){
    return authorRepository.findAll();
  }

  public Author findById(Long id) throws AuthorNotFoundException {
    return authorRepository.findById(id)
        .orElseThrow(AuthorNotFoundException::new);
  }

  public Author createAuthor(Author author) {
    return authorRepository.save(author);
  }

  public Author updateAuthor(Long id, Author author) throws AuthorNotFoundException {
    Author updateAuthor = findById(id);

    updateAuthor.setName(author.getName());
    updateAuthor.setNationality(author.getNationality());

    return authorRepository.save(updateAuthor);
  }

  public Author deleteById(Long id) throws AuthorNotFoundException {
    Author authorToDelete = findById(id);

    authorRepository.delete(authorToDelete);

    return authorToDelete;
  }

}
