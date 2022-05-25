package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IAuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.DAOFactory;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IAuthorService;

import java.util.List;

public class AuthorServiceImpl implements IAuthorService {

    private final IAuthorDAO authorDAO = DAOFactory.getInstance().getAuthorDAO();

    @Override
    public boolean insert(Author autor) throws ServiceException {
        boolean result = false;
        try {
            result =  authorDAO.insert(autor);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Author> findAll() throws ServiceException {
        List<Author> authors = null;
        try {
            authors = authorDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return authors;
    }

    @Override
    public boolean update(Author autor) throws ServiceException {
        boolean result = false;
        try {
            result =  authorDAO.update(autor);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Author findById(int id) throws ServiceException {
        Author optional = null;
        try {
            optional = authorDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return optional;
    }

    @Override
    public boolean changeStatusById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = authorDAO.changeStatusById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}