package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.User;

import java.util.List;

public interface UserDAO {

    boolean createUser(User user) throws DAOException;
    User authenticateUser(String email, String password) throws DAOException;
    List<Menu> findAllMenuByRole(String roleType) throws DAOException;

}