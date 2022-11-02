package pe.edu.unprg.javaee.inventariolibros.service.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.UserDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.UserDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.model.User;
import pe.edu.unprg.javaee.inventariolibros.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean createUser(User user) {
        boolean result;
        try {
            result =  userDAO.createUser(user);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public User authenticateUser(String email, String password) {
        User result;
        try {
            result =  userDAO.authenticateUser(email, password);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}