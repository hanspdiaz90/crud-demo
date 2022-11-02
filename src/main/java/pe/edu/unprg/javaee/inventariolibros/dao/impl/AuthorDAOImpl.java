package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.AuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.AuthorQuery;
import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;
import pe.edu.unprg.javaee.inventariolibros.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public boolean createAuthor(Author author) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_CREATE_AUTHOR)) {
            cstmt.setString(1, author.getFirstname());
            cstmt.setString(2, author.getLastname());
            cstmt.setString(3, author.getCity());
            cstmt.setDate(4, JdbcUtils.toSQLDate(author.getDateBirth()));
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_CREATE_AUTHOR, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editAuthor(Author author) throws DAOException {
        boolean rowsUpdated;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_EDIT_AUTHOR)) {
            cstmt.setString(1, author.getFirstname());
            cstmt.setString(2, author.getLastname());
            cstmt.setString(3, author.getCity());
            cstmt.setDate(4, JdbcUtils.toSQLDate(author.getDateBirth()));
            cstmt.setBoolean(5, author.isActive());
            cstmt.setInt(6, author.getAuthorId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_EDIT_AUTHOR, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Author findByAuthorId(int authorId) throws DAOException {
        Author author = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_FIND_AUTHOR_BY_ID)) {
            cstmt.setInt(1, authorId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setFirstname(rs.getString("firstname"));
                author.setLastname(rs.getString("lastname"));
                author.setCity(rs.getString("city"));
                author.setDateBirth(JdbcUtils.toLocalDate(rs.getDate("birth_date")));
                author.setActive(rs.getBoolean("active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_FIND_AUTHOR_BY_ID, ex);
        }
        return author;
    }

    @Override
    public List<Author> findAll() throws DAOException {
        List<Author> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_FIND_ALL_AUTHORS);
             ResultSet rs = cstmt.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setFirstname(rs.getString("firstname"));
                author.setLastname(rs.getString("lastname"));
                author.setCity(rs.getString("city"));
                author.setDateBirth(JdbcUtils.toLocalDate(rs.getDate("birth_date")));
                author.setActive(rs.getBoolean("active"));
                result.add(author);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_FIND_ALL_AUTHORS, ex);
        }
        return result;
    }

    @Override
    public boolean disableByAuthorId(int authorId) throws DAOException {
        boolean rowsAffected;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_DISABLE_AUTHOR_BY_ID)) {
            cstmt.setInt(1, authorId);
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