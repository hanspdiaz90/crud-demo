package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.UserDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.UserQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Role;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;
import pe.edu.unprg.javaee.cruddemo.utils.enums.UserRoleType;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

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
             CallableStatement cstmt = conn.prepareCall(UserQuery.LOGIN_USER)) {
            cstmt.setString(1, email);
            cstmt.setString(2, password);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                Role role = new Role();
                role.setRoleId(rs.getInt("role_id"));
                String roleType = rs.getString("role_type");
                role.setRoleType(Stream.of(UserRoleType.values())
                        .filter(userRoleType -> userRoleType.getKey().equals(roleType))
                        .findFirst()
                        .get());
                role.setRoot(rs.getBoolean("is_root"));
                user.setRole(role);
                user.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.LOGIN_USER, ex);
        }
        return user;
    }

}