package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;

public interface DashboardDAO {

    int countAllAuthors() throws DAOException;
    int countAllBooks() throws DAOException;
    int countAllGenres() throws DAOException;
    int countAllPublishers() throws DAOException;

}