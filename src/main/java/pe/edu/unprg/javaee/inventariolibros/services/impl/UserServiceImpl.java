package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IUserDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.LibraryDAOFactory;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.models.User;
import pe.edu.unprg.javaee.inventariolibros.services.IUserService;

public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO = LibraryDAOFactory.getInstance().getUserDAO();

    @Override
    public boolean insert(User user) throws ServiceException {
        boolean result = false;
        try {
            result =  userDAO.insert(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User authenticate(String email, String password) throws ServiceException {
        User result = null;
        try {
            result =  userDAO.authenticate(email, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}