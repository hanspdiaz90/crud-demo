package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.MenuDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.MenuDAOImpl;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.service.MenuService;

import java.util.List;

public class MenuServiceImpl implements MenuService {

    private final MenuDAO menuDAO = new MenuDAOImpl();

    @Override
    public boolean createMenu(Menu menu) {
        boolean result;
        try {
            result =  this.menuDAO.createMenu(menu);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editMenu(Menu module) {
        boolean result;
        try {
            result =  this.menuDAO.editMenu(module);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Menu findByMenuId(int menuId) {
        Menu result;
        try {
            result = this.menuDAO.findByMenuId(menuId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> result;
        try {
            result = this.menuDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Module> findActiveModules() {
        List<Module> result;
        try {
            result = this.menuDAO.findActiveModules();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByMenuId(int menuId) {
        boolean result;
        try {
            result = this.menuDAO.disableByMenuId(menuId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}