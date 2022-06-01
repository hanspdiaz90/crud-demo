package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IPublisherDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.LibraryDAOFactory;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IPublisherService;

import java.util.List;

public class PublisherServiceImpl implements IPublisherService {

    private final IPublisherDAO publisherDAO = LibraryDAOFactory.getInstance().getPublisherDAO();

    @Override
    public boolean insert(Publisher publisher) throws ServiceException {
        boolean result = false;
        try {
            result =  publisherDAO.insert(publisher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Publisher publisher) throws ServiceException {
        boolean result = false;
        try {
            result =  publisherDAO.update(publisher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Publisher findById(int id) throws ServiceException {
        Publisher result = null;
        try {
            result = publisherDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Publisher> findAll() throws ServiceException {
        List<Publisher> result = null;
        try {
            result = publisherDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = publisherDAO.disableById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}