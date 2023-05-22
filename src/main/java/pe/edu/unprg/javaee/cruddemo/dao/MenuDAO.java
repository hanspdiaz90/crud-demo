package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;

import java.util.List;

public interface MenuDAO {

    List<Menu> findAllMenuByRole(String roleType) throws DAOException;

}