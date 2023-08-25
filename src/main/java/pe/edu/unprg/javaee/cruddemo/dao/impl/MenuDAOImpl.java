package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.MenuDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.MenuQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.utils.Constants;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    @Override
    public boolean createMenu(Menu menu) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(MenuQuery.INSERT_MENU)) {
            cstmt.setString(1, menu.getTitle());
            cstmt.setString(2, menu.getIcon());
            cstmt.setString(3, menu.getRoute());
            cstmt.setInt(4, menu.getModule().getModuleId());
            cstmt.setObject(5, menu.getParentId());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + MenuQuery.INSERT_MENU, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editMenu(Menu menu) throws DAOException {
        return false;
    }

    @Override
    public Menu findByMenuId(int menuId) throws DAOException {
        return null;
    }

    @Override
    public List<Menu> findAll() throws DAOException {
        List<Menu> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(MenuQuery.FIND_ALL_MENUS)) {
            cstmt.setString(1, Constants.NULL_STRING_PARAMETER);
            cstmt.setInt(2, Constants.NOT_INTEGER_PARAMETER);
            cstmt.setInt(3, Constants.NOT_INTEGER_PARAMETER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Module module = new Module();
                module.setModuleId(rs.getInt("module_id"));
                module.setTitle(rs.getString("module"));
                Menu menu = new Menu();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setTitle(rs.getString("title"));
                menu.setModule(module);
                menu.setIcon(rs.getString("icon"));
                menu.setRoute(rs.getString("route"));
                menu.setParentId(rs.getInt("parent_id"));
                menu.setSortOrder(rs.getInt("sort_order"));
                menu.setActive(rs.getBoolean("is_active"));
                result.add(menu);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + MenuQuery.FIND_ALL_MENUS, ex);
        }
        return result;
    }

    @Override
    public List<Module> findActiveModules() throws DAOException {
        List<Module> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(MenuQuery.FIND_ACTIVE_MODULES)) {
            cstmt.setString(1, Constants.NULL_STRING_PARAMETER);
            cstmt.setBoolean(2, Constants.ACTIVE_BOOLEAN_PARAMETER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Module module = new Module();
                module.setModuleId(rs.getInt("module_id"));
                module.setTitle(rs.getString("title"));
                module.setActive(rs.getBoolean("is_active"));
                result.add(module);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + MenuQuery.FIND_ACTIVE_MODULES, ex);
        }
        return result;
    }

    @Override
    public boolean disableByMenuId(int menuId) throws DAOException {
        return false;
    }
}