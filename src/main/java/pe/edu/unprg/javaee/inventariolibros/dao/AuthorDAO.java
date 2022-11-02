package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface AuthorDAO {

    boolean createAuthor(Author author) throws DAOException;
    boolean editAuthor(Author author) throws DAOException;
    Author findByAuthorId(int authorId) throws DAOException;
    List<Author> findAll() throws DAOException;
    boolean disableByAuthorId(int authorId) throws DAOException;
//    boolean changeStatusById(int id) throws DAOException;

}