package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.UserDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.UserQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.model.enums.Role;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean createUser(User user) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_INSERT_USER)) {
            cstmt.setString(1, user.getEmail());
            cstmt.setString(2, user.getPassword());
            cstmt.setInt(3, user.getRole().getStatusId());
            cstmt.setBoolean(4, user.isAdmin());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_INSERT_USER, ex);
        }
        return rowsInserted;
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
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setActivo(rs.getBoolean("active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_LOGIN_USER, ex);
        }
        return user;
    }

}