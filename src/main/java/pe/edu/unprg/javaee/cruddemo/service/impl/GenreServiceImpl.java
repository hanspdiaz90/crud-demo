package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.GenreDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.GenreDAOImpl;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    private final GenreDAO genreDAO = new GenreDAOImpl();

    @Override
    public boolean createGenre(Genre genre) {
        boolean result;
        try {
            result =  this.genreDAO.createGenre(genre);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editGenre(Genre genre) {
        boolean result;
        try {
            result =  this.genreDAO.editGenre(genre);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Genre findByGenreId(int genreId) {
        Genre result;
        try {
            result = this.genreDAO.findByGenreId(genreId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> result;
        try {
            result = this.genreDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByGenreId(int genreId) {
        boolean result;
        try {
            result = this.genreDAO.disableByGenreId(genreId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}