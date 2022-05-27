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
        Publisher publisher = null;
        try {
            publisher = publisherDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return publisher;
    }

    @Override
    public List<Publisher> findAll() throws ServiceException {
        List<Publisher> publishers = null;
        try {
            publishers = publisherDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return publishers;
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