package pe.edu.unprg.javaee.inventariolibros.service;

import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.model.Genre;
import pe.edu.unprg.javaee.inventariolibros.model.Publisher;
import pe.edu.unprg.javaee.inventariolibros.model.Book;

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