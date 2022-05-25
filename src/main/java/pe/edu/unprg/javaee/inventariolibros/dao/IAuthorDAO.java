package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface IAuthorDAO {

    boolean insert(Author author) throws DAOException;
    boolean update(Author author) throws DAOException;
    Author findById(int id) throws DAOException;
    List<Author> findAll() throws DAOException;
    boolean changeStatusById(int id) throws DAOException;
    boolean deactivateById(int id) throws DAOException;

}