package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.model.Book;
import pe.edu.unprg.javaee.inventariolibros.model.Genre;
import pe.edu.unprg.javaee.inventariolibros.model.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

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