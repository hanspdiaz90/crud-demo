package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IPublisherDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.DAOFactory;
import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IPublisherService;

import java.util.List;

public class PublisherServiceImpl implements IPublisherService {

    private final IPublisherDAO editorialDAO = DAOFactory.getInstance().getPublisherDAO();

    @Override
    public boolean insert(Publisher publisher) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  editorialDAO.insert(publisher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public boolean update(Publisher publisher) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  editorialDAO.update(publisher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public Publisher findById(int id) throws ServiceException {
        Publisher editorial = null;
        try {
            editorial = editorialDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return editorial;
    }

    @Override
    public List<Publisher> findAll() throws ServiceException {
        List<Publisher> listaEditoriales = null;
        try {
            listaEditoriales = editorialDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaEditoriales;
    }

    @Override
    public boolean deactivateById(int id) throws ServiceException {
        boolean resultado = false;
        try {
            resultado = editorialDAO.deactivateById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

}