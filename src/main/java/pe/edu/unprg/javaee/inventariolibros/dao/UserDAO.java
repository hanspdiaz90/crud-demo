package pe.edu.unprg.javaee.inventariolibros.dao;

import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.model.User;

public interface UserDAO {

    boolean createUser(User user) throws DAOException;
    User authenticateUser(String email, String password) throws DAOException;

}