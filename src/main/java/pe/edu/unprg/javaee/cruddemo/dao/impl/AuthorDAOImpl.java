package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.AuthorDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.AuthorQuery;
import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.utils.Constants;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;
import pe.edu.unprg.javaee.cruddemo.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    @Override
    public boolean createAuthor(Author author) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.INSERT_AUTHOR)) {
            cstmt.setString(1, author.getFirstName());
            cstmt.setString(2, author.getLastName());
            cstmt.setString(3, author.getCity());
            cstmt.setDate(4, JdbcUtils.toSQLDate(author.getDob()));
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.INSERT_AUTHOR, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editAuthor(Author author) throws DAOException {
        boolean rowsUpdated;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.UPDATE_AUTHOR)) {
            cstmt.setString(1, author.getFirstName());
            cstmt.setString(2, author.getLastName());
            cstmt.setString(3, author.getCity());
            cstmt.setDate(4, JdbcUtils.toSQLDate(author.getDob()));
            cstmt.setBoolean(5, author.isActive());
            cstmt.setInt(6, author.getAuthorId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.UPDATE_AUTHOR, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Author findByAuthorId(int authorId) throws DAOException {
        Author author = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.FIND_AUTHOR_BY_ID)) {
            cstmt.setInt(1, authorId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setFirstName(rs.getString("first_name"));
                author.setLastName(rs.getString("last_name"));
                author.setCity(rs.getString("city"));
                author.setDob(JdbcUtils.toLocalDate(rs.getDate("dob")));
                author.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.FIND_AUTHOR_BY_ID, ex);
        }
        return author;
    }

    @Override
    public List<Author> findAll() throws DAOException {
        List<Author> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.FIND_ALL_AUTHORS)) {
            cstmt.setString(1, Constants.NULL_STRING_PARAMETER);
            cstmt.setInt(2, Constants.NOT_INTEGER_PARAMETER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setFirstName(rs.getString("first_name"));
                author.setLastName(rs.getString("last_name"));
                author.setCity(rs.getString("city"));
                author.setDob(JdbcUtils.toLocalDate(rs.getDate("dob")));
                author.setActive(rs.getBoolean("is_active"));
                result.add(author);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.FIND_ALL_AUTHORS, ex);
        }
        return result;
    }

    @Override
    public boolean disableByAuthorId(int authorId) throws DAOException {
        boolean rowsAffected;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.DISABLE_AUTHOR_BY_ID)) {
            cstmt.setInt(1, authorId);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.DISABLE_AUTHOR_BY_ID, ex);
        }
        return rowsAffected;
    }

    /*
    @Override
    public boolean changeStatusById(int id) throws DAOException {
        boolean rowsAffected = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_CHANGE_AUTHOR_STATUS_BY_ID)) {
            cstmt.setInt(1, id);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_CHANGE_AUTHOR_STATUS_BY_ID, ex);
        }
        return rowsAffected;
    }
     */

}