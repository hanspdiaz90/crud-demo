package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.models.User;

public interface IUserDAO {

    boolean insert(User user) throws DAOException;
    User authenticate(String email, String password) throws DAOException;

}