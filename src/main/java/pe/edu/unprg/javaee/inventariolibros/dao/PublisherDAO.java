package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.model.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

import java.util.List;

public interface PublisherDAO {

    boolean createPublisher(Publisher publisher) throws DAOException;
    boolean editPublisher(Publisher publisher) throws DAOException;
    Publisher findByPublisherId(int publisherId) throws DAOException;
    List<Publisher> findAll() throws DAOException;
    boolean disableByPublisherId(int publisherId) throws DAOException;

}