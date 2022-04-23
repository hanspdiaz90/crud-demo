package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IGenreDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.DAOFactory;
import pe.edu.unprg.javaee.inventariolibros.entities.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IGenreService;

import java.util.List;

public class GenreServiceImpl implements IGenreService {

    private final IGenreDAO generoDAO = DAOFactory.getInstance().getGenreDAO();

    @Override
    public boolean insert(Genre genre) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  generoDAO.insert(genre);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public boolean update(Genre genre) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  generoDAO.update(genre);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public Genre findById(int id) throws ServiceException {
        Genre genero = null;
        try {
            genero = generoDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return genero;
    }

    @Override
    public List<Genre> findAll() throws ServiceException {
        List<Genre> listaGeneros = null;
        try {
            listaGeneros = generoDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaGeneros;
    }

    @Override
    public boolean deactivateById(int id) throws ServiceException {
        boolean resultado = false;
        try {
            resultado = generoDAO.deactivateById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

}