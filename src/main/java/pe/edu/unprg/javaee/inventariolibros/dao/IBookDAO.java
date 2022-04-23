package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.entities.Author;
import pe.edu.unprg.javaee.inventariolibros.entities.Book;
import pe.edu.unprg.javaee.inventariolibros.entities.Genre;
import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface IBookDAO {

    boolean insert(Book book) throws DAOException;
    boolean update(Book book) throws DAOException;
    Book findById(int id) throws DAOException;
    List<Book> findAllBooks() throws DAOException;
    List<Author> findActiveAuthors(String filter) throws DAOException;
    List<Publisher> findActivePublishers(String filter) throws DAOException;
    List<Genre> findActiveGenres(String filtro) throws DAOException;
    boolean deactivateById(int id) throws DAOException;

}