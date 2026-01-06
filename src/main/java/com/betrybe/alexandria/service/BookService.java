package com.betrybe.alexandria.service;

import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.repository.BookRepository;
import com.betrybe.alexandria.service.exception.BookNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book findById(Long id) throws BookNotFoundException {

    return bookRepository.findById(id)
        .orElseThrow(BookNotFoundException::new);
  }

  public List<Book> findAll(){
    return bookRepository.findAll();
  }

  public Book create(Book book) {
    return bookRepository.save(book);
  }

  public Book update(Long id, Book book) throws BookNotFoundException {
    Book updateBook = findById(id);
    updateBook.setTitle(book.getTitle());
    updateBook.setGenre(book.getGenre());

    return bookRepository.save(updateBook);
  }

  public Book deleteById(Long id) throws BookNotFoundException {
    Book book = findById(id);

    bookRepository.deleteById(id);

    return book;
  }

}
