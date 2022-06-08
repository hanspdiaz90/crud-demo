package pe.edu.unprg.javaee.inventariolibros.services;

import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.models.User;

public interface IUserService {

    boolean insert(User user) throws ServiceException;
    User validate(String email, String password) throws ServiceException;

}