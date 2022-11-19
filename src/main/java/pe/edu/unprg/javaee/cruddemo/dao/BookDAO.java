package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.model.Book;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;

import java.util.List;

public interface BookDAO {

    boolean createBook(Book book) throws DAOException;
    boolean editBook(Book book) throws DAOException;
    Book findByBookId(int bookId) throws DAOException;
    List<Book> findAll() throws DAOException;
    List<Author> findActiveAuthors(String filter) throws DAOException;
    List<Publisher> findActivePublishers(String filter) throws DAOException;
    List<Genre> findActiveGenres(String filter) throws DAOException;
    boolean disableByBookId(int bookId) throws DAOException;

}