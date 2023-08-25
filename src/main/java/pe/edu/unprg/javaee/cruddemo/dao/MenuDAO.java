package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.Module;

import java.util.List;

public interface MenuDAO {

    boolean createMenu(Menu menu) throws DAOException;
    boolean editMenu(Menu menu) throws DAOException;
    Menu findByMenuId(int menuId) throws DAOException;
    List<Menu> findAll() throws DAOException;
    List<Module> findActiveModules() throws DAOException;
    boolean disableByMenuId(int menuId) throws DAOException;

}