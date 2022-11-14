package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;

import java.util.List;

public interface GenreDAO {

    boolean createGenre(Genre genre) throws DAOException;
    boolean editGenre(Genre genre) throws DAOException;
    Genre findByGenreId(int genreId) throws DAOException;
    List<Genre> findAll() throws DAOException;
    boolean disableByGenreId(int genreId) throws DAOException;

}