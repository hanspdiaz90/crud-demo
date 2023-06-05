package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;
import pe.edu.unprg.javaee.cruddemo.model.User;

import java.util.List;

public interface UserService {

    boolean createUser(User user);
    User authenticateUser(String email, String password);
    List<MenuPermission> findAllMenuPermissionByRole(Integer roleId);

}
