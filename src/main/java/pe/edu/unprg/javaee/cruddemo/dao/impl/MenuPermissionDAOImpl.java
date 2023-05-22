package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.MenuPermissionDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.MenuPermissionQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;
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
             CallableStatement cstmt = conn.prepareCall(MenuPermissionQuery.SP_FIND_ALL_MENU_BY_ROLE_RECURSIVE)) {
            cstmt.setInt(1, roleId);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                MenuPermission menuPermission = new MenuPermission();
                menuPermission.setMenuId(rs.getInt("menu_id"));
                menuPermission.setTitle(rs.getString("title"));
                menuPermission.setModule(rs.getString("module"));
                menuPermission.setIcon(rs.getString("icon"));
                menuPermission.setRoute(rs.getString("route"));
                menuPermission.setParentId(rs.getInt("parent_id"));
                menuPermission.setOrder(rs.getInt("sort_order"));
                menuPermission.setLevel(rs.getInt("level"));
                menuPermission.setRead(rs.getBoolean("can_read"));
                menuPermission.setWrite(rs.getBoolean("can_write"));
                menuPermission.setDelete(rs.getBoolean("can_delete"));
                result.add(menuPermission);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + MenuPermissionQuery.SP_FIND_ALL_MENU_BY_ROLE_RECURSIVE, ex);
        }
        return result;
    }

}