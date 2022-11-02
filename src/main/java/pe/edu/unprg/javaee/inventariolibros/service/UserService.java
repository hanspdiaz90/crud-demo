package pe.edu.unprg.javaee.inventariolibros.service;

import pe.edu.unprg.javaee.inventariolibros.model.User;

public interface UserService {

    boolean createUser(User user);
    User authenticateUser(String email, String password);

}