package com.betrybe.alexandria.service;

import com.betrybe.alexandria.entity.Author;
import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.entity.BookDetail;
import com.betrybe.alexandria.entity.Publisher;
import com.betrybe.alexandria.repository.BookDetailRepository;
import com.betrybe.alexandria.repository.BookRepository;
import com.betrybe.alexandria.service.exception.AuthorNotFoundException;
import com.betrybe.alexandria.service.exception.BookDetailNotFoundException;
import com.betrybe.alexandria.service.exception.BookNotFoundException;
import com.betrybe.alexandria.service.exception.PublisherNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final BookDetailRepository bookDetailRepository;
  private final PublisherService publisherService;
  private final AuthorService authorService;

  @Autowired
  public BookService(BookRepository bookRepository, BookDetailRepository bookDetailRepository,
      PublisherService publisherService, AuthorService authorService) {
    this.bookRepository = bookRepository;
    this.bookDetailRepository = bookDetailRepository;
    this.publisherService = publisherService;
    this.authorService = authorService;
  }

  public Book findById(Long id) throws BookNotFoundException {

    return bookRepository.findById(id)
        .orElseThrow(BookNotFoundException::new);
  }

  public List<Book> findAll(int pageNumber, int pageSize){
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Book> page = bookRepository.findAll(pageable);
    return page.toList();
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

  //Agora é possivel criar metodos para bookDetails

  public BookDetail createBookDetail(Long bookId, BookDetail bookDetail)
      throws BookNotFoundException {
    Book book = findById(bookId);

    bookDetail.setBook(book);

    return bookDetailRepository.save(bookDetail);
  }

  public BookDetail getBookDetailById(Long id)
      throws BookNotFoundException, BookDetailNotFoundException {
    Book findBook = findById(id);

    BookDetail bookDetail = findBook.getDetail();

    if(bookDetail == null) {
      throw new BookDetailNotFoundException();
    }
    return bookDetail;
  }

  public BookDetail updateBookDetail(Long id, BookDetail bookDetail)
      throws BookNotFoundException, BookDetailNotFoundException {

    //Procuro pelo detalhe do livro no banco atraves do id
    BookDetail bookDetailFromDB = getBookDetailById(id);

    //altero os atributos dos detalhes do livro
    bookDetailFromDB.setSummary(bookDetail.getSummary());
    bookDetailFromDB.setPageCount(bookDetail.getPageCount());
    bookDetailFromDB.setYear(bookDetail.getYear());
    bookDetailFromDB.setIsbn(bookDetail.getIsbn());

    //salvo a alteraçao
    return bookDetailRepository.save(bookDetailFromDB);

  }

  public BookDetail deleteBookDetail(Long id)
      throws BookDetailNotFoundException, BookNotFoundException {

    //Procuro pelo livro no banco
    Book book = findById(id);
    //Pego o detalhe daquele livro no banco
    BookDetail bookDetail = book.getDetail();

    //se o livro nao tiver detalhe, lano uma exceção
    if(bookDetail == null) {
      throw new BookDetailNotFoundException();
    }

    //se tiver, seto o detalhe do livro como vazio
    book.setDetail(null);
    //seto o livro que esta linkado ao detalhe como vazio
    bookDetail.setBook(null);

    //delelto o detalhe do banco
    bookDetailRepository.delete(bookDetail);

    return bookDetail;
  }

  public Book setBookPublisher(Long bookId, Long publisherId)
      throws BookNotFoundException, PublisherNotFoundException {
    Book findBook = findById(bookId);
    Publisher publisher = publisherService.findById(publisherId);

    findBook.setPublisher(publisher);

    return bookRepository.save(findBook);
  }

  public Book removeBookPublisher(Long bookId) throws BookNotFoundException {
    Book book = findById(bookId);
    book.setPublisher(null);

    return bookRepository.save(book);

  }

  public Book addBookAuthor(Long bookId, Long authorId)
      throws BookNotFoundException, AuthorNotFoundException {
    Book book = findById(bookId);
    Author author = authorService.findById(authorId);

    book.getAuthors().add(author);

    return bookRepository.save(book);
  }


  public Book removeBookAuthor(Long bookId, Long authorId)
      throws BookNotFoundException, AuthorNotFoundException {
    Book book = findById(bookId);
    Author author = authorService.findById(authorId);

    book.getAuthors().remove(author);

    return bookRepository.save(book);
  }
}
