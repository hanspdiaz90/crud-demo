package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IAuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.LibraryDAOFactory;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IAuthorService;

import java.util.List;

public class AuthorServiceImpl implements IAuthorService {

    private final IAuthorDAO authorDAO = LibraryDAOFactory.getInstance().getAuthorDAO();

    @Override
    public boolean insert(Author author) throws ServiceException {
        boolean result = false;
        try {
            result =  authorDAO.insert(author);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Author author) throws ServiceException {
        boolean result = false;
        try {
            result =  authorDAO.update(author);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Author findById(int id) throws ServiceException {
        Author result = null;
        try {
            result = authorDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Author> findAll() throws ServiceException {
        List<Author> result = null;
        try {
            result = authorDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = authorDAO.disableById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

//    @Override
//    public boolean changeStatusById(int id) throws ServiceException {
//        boolean result = false;
//        try {
//            result = authorDAO.changeStatusById(id);
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        }
//        return result;
//    }

}