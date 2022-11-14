package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.model.Book;

import java.util.List;

public interface BookService {

    boolean createBook(Book book);
    boolean editBook(Book book);
    Book findByBookId(int bookId);
    List<Book> findAll();
    List<Author> findActiveAuthors(String filter);
    List<Publisher> findActivePublishers(String filter);
    List<Genre> findActiveGenres(String filter);
    boolean disableByBookId(int bookId);

}