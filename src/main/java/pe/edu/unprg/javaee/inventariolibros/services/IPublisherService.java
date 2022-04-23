package pe.edu.unprg.javaee.inventariolibros.services;

import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;

import java.util.List;

public interface IPublisherService {

    boolean insert(Publisher publisher) throws ServiceException;
    boolean update(Publisher publisher) throws ServiceException;
    Publisher findById(int id) throws ServiceException;
    List<Publisher> findAll() throws ServiceException;
    boolean deactivateById(int id) throws ServiceException;

}