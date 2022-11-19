package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.User;

public interface UserService {

    boolean createUser(User user);
    User authenticateUser(String email, String password);

}