package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IAuthorDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.AuthorQuery;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements IAuthorDAO {

    @Override
    public boolean insert(Author author) throws DAOException {
        boolean insertedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_INSERT_AUTHOR)) {
            cstmt.setString(1, author.getNombres());
            cstmt.setString(2, author.getApellidos());
            cstmt.setString(3, author.getCiudad());
            insertedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_INSERT_AUTHOR, ex);
        }
        return insertedRow;
    }

    @Override
    public boolean update(Author author) throws DAOException {
        boolean updatedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_UPDATE_AUTHOR)) {
            cstmt.setString(1, author.getNombres());
            cstmt.setString(2, author.getApellidos());
            cstmt.setString(3, author.getCiudad());
            cstmt.setBoolean(4, author.isActivo());
            cstmt.setInt(5, author.getId());
            updatedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_UPDATE_AUTHOR, ex);
        }
        return updatedRow;
    }

    @Override
    public Author findById(int id) throws DAOException {
        Author optional = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_FIND_AUTHOR_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                optional = new Author(rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("ciudad"),
                        rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_FIND_AUTHOR_BY_ID, ex);
        }
        return optional;
    }

    @Override
    public List<Author> findAll() throws DAOException {
        List<Author> authors = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_FIND_ALL_AUTHORS);
             ResultSet rs = cstmt.executeQuery()) {
            authors = new ArrayList<>();
            while (rs.next()) {
                Author optional = new Author(rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("ciudad"),
                        rs.getBoolean("activo"));
                authors.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_FIND_ALL_AUTHORS, ex);
        }
        return authors;
    }

    @Override
    public int contarTodosAutores() throws DAOException {
        int count = 0;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_CONTAR_TODOS_AUTORES)) {
            cstmt.registerOutParameter(1, Types.INTEGER);
            if (cstmt.executeUpdate() > 0) {
                count = cstmt.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_CONTAR_TODOS_AUTORES, ex);
        }
        return count;
    }

    @Override
    public boolean changeStatusById(int id) throws DAOException {
        boolean affectedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(AuthorQuery.SP_CHANGE_AUTHOR_STATUS_BY_ID)) {
            cstmt.setInt(1, id);
            affectedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + AuthorQuery.SP_CHANGE_AUTHOR_STATUS_BY_ID, ex);
        }
        return affectedRow;
    }

}