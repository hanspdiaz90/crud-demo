package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.MenuDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.UserQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    @Override
    public List<Menu> findAllMenuByRole(String roleType) throws DAOException {
        List<Menu> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_FIND_ALL_MENU_BY_ROLE)) {
            cstmt.setString(1, roleType);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setTitle(rs.getString("title"));
                menu.setModule(rs.getString("module"));
                menu.setIcon(rs.getString("icon"));
                menu.setRoute(rs.getString("route"));
                menu.setParentId(rs.getInt("parent_id"));
                menu.setOrder(rs.getInt("sort_order"));
                result.add(menu);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_FIND_ALL_MENU_BY_ROLE, ex);
        }
        return result;
    }

}