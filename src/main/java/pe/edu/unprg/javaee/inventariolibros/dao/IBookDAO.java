package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.models.Book;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface IBookDAO {

    boolean insert(Book book) throws DAOException;
    boolean update(Book book) throws DAOException;
    Book findById(int id) throws DAOException;
    List<Book> findAll() throws DAOException;
    List<Author> findActiveAuthors(String filter) throws DAOException;
    List<Publisher> findActivePublishers(String filter) throws DAOException;
    List<Genre> findActiveGenres(String filter) throws DAOException;
    boolean disableById(int id) throws DAOException;

}