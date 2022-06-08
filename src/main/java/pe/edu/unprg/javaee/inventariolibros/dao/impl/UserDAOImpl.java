package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IUserDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.UserQuery;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.models.User;
import pe.edu.unprg.javaee.inventariolibros.models.enums.Rol;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements IUserDAO {

    @Override
    public boolean insert(User user) throws DAOException {
        boolean rowsInserted = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_INSERT_USER)) {
            cstmt.setString(1, user.getEmail());
            cstmt.setString(2, user.getContrasenia());
            cstmt.setInt(3, user.getRol().getStatusId());
            cstmt.setBoolean(4, user.isAdmin());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_INSERT_USER, ex);
        }
        return rowsInserted;
    }

    @Override
    public User validate(String email, String password) throws DAOException {
        User user = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(UserQuery.SP_VALIDATE_USER)) {
            cstmt.setString(1, email);
            cstmt.setString(2, password);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setContrasenia(rs.getString("contrasenia"));
                user.setRol(Rol.valueOf(rs.getString("rol")));
                user.setAdmin(rs.getBoolean("admin"));
                user.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + UserQuery.SP_VALIDATE_USER, ex);
        }
        return user;
    }

}