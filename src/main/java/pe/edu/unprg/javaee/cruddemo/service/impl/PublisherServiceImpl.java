package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.PublisherDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.PublisherDAOImpl;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.service.PublisherService;

import java.util.List;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherDAO publisherDAO = new PublisherDAOImpl();

    @Override
    public boolean createPublisher(Publisher publisher) {
        boolean result;
        try {
            result =  publisherDAO.createPublisher(publisher);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editPublisher(Publisher publisher) {
        boolean result;
        try {
            result =  publisherDAO.editPublisher(publisher);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Publisher findByPublisherId(int publisherId) {
        Publisher result;
        try {
            result = publisherDAO.findByPublisherId(publisherId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Publisher> findAll() {
        List<Publisher> result;
        try {
            result = publisherDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByPublisherId(int publisherId) {
        boolean result;
        try {
            result = publisherDAO.disableByPublisherId(publisherId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}