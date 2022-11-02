package pe.edu.unprg.javaee.inventariolibros.service.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.GenreDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.GenreDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.model.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {

    private final GenreDAO genreDAO = new GenreDAOImpl();

    @Override
    public boolean createGenre(Genre genre) {
        boolean result;
        try {
            result =  genreDAO.createGenre(genre);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editGenre(Genre genre) {
        boolean result;
        try {
            result =  genreDAO.editGenre(genre);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Genre findByGenreId(int genreId) {
        Genre result;
        try {
            result = genreDAO.findByGenreId(genreId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Genre> findAll() {
        List<Genre> result;
        try {
            result = genreDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByGenreId(int genreId) {
        boolean result;
        try {
            result = genreDAO.disableByGenreId(genreId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}