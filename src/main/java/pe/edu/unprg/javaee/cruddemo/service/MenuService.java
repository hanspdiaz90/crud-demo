package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.Module;

import java.util.List;

public interface MenuService {

    boolean createMenu(Menu menu);
    boolean editMenu(Menu menu);
    Menu findByMenuId(int menuId);
    List<Menu> findAll();
    List<Module> findActiveModules();
    boolean disableByMenuId(int menuId);

}