package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.User;

public interface UserDAO {

    boolean createUser(User user) throws DAOException;
    User authenticateUser(String email, String password) throws DAOException;

}