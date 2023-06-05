package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.MenuPermissionDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.MenuPermissionQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuPermissionDAOImpl implements MenuPermissionDAO {

    @Override
    public List<MenuPermission> findAllMenuPermissionByRole(Integer roleId) throws DAOException {
        List<MenuPermission> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(MenuPermissionQuery.SP_FIND_ALL_MENU_BY_ROLE)) {
            cstmt.setInt(1, roleId);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Module module = new Module();
                module.setModuleId(rs.getInt("module_id"));
                module.setTitle(rs.getString("module"));
                Menu menu = new Menu();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setTitle(rs.getString("menu"));
                menu.setModule(module);
                menu.setIcon(rs.getString("icon"));
                menu.setRoute(rs.getString("route"));
                menu.setParentId(rs.getInt("parent_id"));
                menu.setSortOrder(rs.getInt("sort_order"));
                MenuPermission permission = new MenuPermission();
                permission.setMenu(menu);
                permission.setLevel(rs.getInt("level"));
                permission.setRead(rs.getBoolean("can_read"));
                permission.setWrite(rs.getBoolean("can_write"));
                permission.setDelete(rs.getBoolean("can_delete"));
                result.add(permission);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + MenuPermissionQuery.SP_FIND_ALL_MENU_BY_ROLE, ex);
        }
        return result;
    }

}