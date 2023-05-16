package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.UserDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.UserQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.Role;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;
import pe.edu.unprg.javaee.cruddemo.utils.enums.RoleType;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean createUser(User user) throws DAOException {
        /*
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_INSERT_USER)) {
            cstmt.setString(1, user.getEmail());
            cstmt.setString(2, user.getPassword());
            cstmt.setInt(3, user.getRole().getStatusId());
            cstmt.setInt(4, user.getStatus().getStatusId());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_INSERT_USER, ex);
        }
        return rowsInserted;
         */
        return true;
    }

    @Override
    public User authenticateUser(String email, String password) throws DAOException {
        User user = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_LOGIN_USER)) {
            cstmt.setString(1, email);
            cstmt.setString(2, password);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));

                Role role = new Role();
                role.setRoleType(RoleType.valueOf(rs.getString("role_type")));
                role.setRoot(rs.getBoolean("is_root"));

                user.setRole(role);
                user.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_LOGIN_USER, ex);
        }
        return user;
    }

    @Override
    public List<Menu> findAllMenuByRole(String roleType) throws DAOException {
        List<Menu> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_FIND_MENU_BY_ROLE)) {
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
                menu.setSortOrder(rs.getInt("sort_order"));
                result.add(menu);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_FIND_MENU_BY_ROLE, ex);
        }
        return result;
    }

}