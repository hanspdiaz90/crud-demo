package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;
import pe.edu.unprg.javaee.cruddemo.model.User;

import java.util.List;

public interface MenuPermissionDAO {

    List<MenuPermission> findAllMenuPermissionByRole(Integer roleId) throws DAOException;

}