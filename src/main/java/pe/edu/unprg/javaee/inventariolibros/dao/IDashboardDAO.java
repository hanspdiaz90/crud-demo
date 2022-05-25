package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.models.Author;

import java.util.List;

public interface IDashboardDAO {

    int countAllAuthors() throws DAOException;
    int countAllBooks() throws DAOException;
    int countAllGenres() throws DAOException;
    int countAllPublishers() throws DAOException;

}