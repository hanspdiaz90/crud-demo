package pe.edu.unprg.javaee.inventariolibros.services;

import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;

import java.util.List;

public interface IAuthorService {

    boolean insert(Author author) throws ServiceException;
    boolean update(Author author) throws ServiceException;
    Author findById(int id) throws ServiceException;
    List<Author> findAll() throws ServiceException;
    boolean changeStatusById(int id) throws ServiceException;
    boolean disableById(int id) throws ServiceException;

}