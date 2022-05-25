package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface IPublisherDAO {

    boolean insert(Publisher publisher) throws DAOException;
    boolean update(Publisher publisher) throws DAOException;
    Publisher findById(int id) throws DAOException;
    List<Publisher> findAll() throws DAOException;
    boolean deactivateById(int id) throws DAOException;

}