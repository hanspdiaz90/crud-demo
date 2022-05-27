package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IGenreDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.LibraryDAOFactory;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IGenreService;

import java.util.List;

public class GenreServiceImpl implements IGenreService {

    private final IGenreDAO genreDAO = LibraryDAOFactory.getInstance().getGenreDAO();

    @Override
    public boolean insert(Genre genre) throws ServiceException {
        boolean result = false;
        try {
            result =  genreDAO.insert(genre);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Genre genre) throws ServiceException {
        boolean result = false;
        try {
            result =  genreDAO.update(genre);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Genre findById(int id) throws ServiceException {
        Genre result = null;
        try {
            result = genreDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Genre> findAll() throws ServiceException {
        List<Genre> result = null;
        try {
            result = genreDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = genreDAO.disableById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}