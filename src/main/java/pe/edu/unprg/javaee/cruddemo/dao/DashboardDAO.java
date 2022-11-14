package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;

public interface DashboardDAO {

    int countAllAuthors() throws DAOException;
    int countAllBooks() throws DAOException;
    int countAllGenres() throws DAOException;
    int countAllPublishers() throws DAOException;

}