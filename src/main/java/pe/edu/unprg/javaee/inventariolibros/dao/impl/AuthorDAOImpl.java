package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IAuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.AuthorQuery;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements IAuthorDAO {

    @Override
    public boolean insert(Author author) throws DAOException {
        boolean rowsInserted = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_INSERT_AUTHOR)) {
            cstmt.setString(1, author.getNombres());
            cstmt.setString(2, author.getApellidos());
            cstmt.setString(3, author.getCiudad());
            cstmt.setDate(4, Date.valueOf(author.getFechaNacimiento()));
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_INSERT_AUTHOR, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean update(Author author) throws DAOException {
        boolean rowsUpdated = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_UPDATE_AUTHOR)) {
            cstmt.setString(1, author.getNombres());
            cstmt.setString(2, author.getApellidos());
            cstmt.setString(3, author.getCiudad());
            cstmt.setDate(4, Date.valueOf(author.getFechaNacimiento()));
            cstmt.setBoolean(5, author.isActivo());
            cstmt.setInt(6, author.getId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_UPDATE_AUTHOR, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Author findById(int id) throws DAOException {
        Author author = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_FIND_AUTHOR_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setId(rs.getInt("id"));
                author.setNombres(rs.getString("nombres"));
                author.setApellidos(rs.getString("apellidos"));
                author.setCiudad(rs.getString("ciudad"));
                author.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                author.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_FIND_AUTHOR_BY_ID, ex);
        }
        return author;
    }

    @Override
    public List<Author> findAll() throws DAOException {
        List<Author> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_FIND_ALL_AUTHORS);
             ResultSet rs = cstmt.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setNombres(rs.getString("nombres"));
                author.setApellidos(rs.getString("apellidos"));
                author.setCiudad(rs.getString("ciudad"));
                author.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                author.setActivo(rs.getBoolean("activo"));
                result.add(author);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_FIND_ALL_AUTHORS, ex);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws DAOException {
        boolean rowsAffected = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_DISABLE_AUTHOR_BY_ID)) {
            cstmt.setInt(1, id);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_DISABLE_AUTHOR_BY_ID, ex);
        }
        return rowsAffected;
    }

//    @Override
//    public boolean changeStatusById(int id) throws DAOException {
//        boolean rowsAffected = false;
//        try (Connection conn = DatabaseHandler.getInstance().getConnection();
//             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_CHANGE_AUTHOR_STATUS_BY_ID)) {
//            cstmt.setInt(1, id);
//            rowsAffected = cstmt.executeUpdate() > 0;
//        } catch (SQLException ex) {
//            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_CHANGE_AUTHOR_STATUS_BY_ID, ex);
//        }
//        return rowsAffected;
//    }

}