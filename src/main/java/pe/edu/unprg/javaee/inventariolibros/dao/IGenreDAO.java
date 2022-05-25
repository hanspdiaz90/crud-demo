package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface IGenreDAO {

    boolean insert(Genre genre) throws DAOException;
    boolean update(Genre genre) throws DAOException;
    Genre findById(int id) throws DAOException;
    List<Genre> findAll() throws DAOException;
    boolean deactivateById(int id) throws DAOException;

}