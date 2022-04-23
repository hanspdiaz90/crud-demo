package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IGenreDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.GenreQuery;
import pe.edu.unprg.javaee.inventariolibros.entities.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements IGenreDAO {

    @Override
    public boolean insert(Genre genre) throws DAOException {
        boolean insertedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_INSERT_GENRE)) {
            cstmt.setString(1, genre.getNombre());
            insertedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_INSERT_GENRE, ex);
        }
        return insertedRow;
    }

    @Override
    public boolean update(Genre genre) throws DAOException {
        boolean updatedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_UPDATE_GENRE)) {
            cstmt.setString(1, genre.getNombre());
            cstmt.setBoolean(4, genre.isActivo());
            cstmt.setInt(5, genre.getId());
            updatedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_UPDATE_GENRE, ex);
        }
        return updatedRow;
    }

    @Override
    public Genre findById(int id) throws DAOException {
        Genre optional = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_FIND_GENRE_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                optional = new Genre(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_FIND_GENRE_BY_ID, ex);
        }
        return optional;
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> genres = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_FIND_ALL_GENRES);
             ResultSet rs = cstmt.executeQuery()) {
            genres = new ArrayList<>();
            while (rs.next()) {
                Genre optional = new Genre(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBoolean("activo"));
                genres.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_FIND_ALL_GENRES, ex);
        }
        return genres;
    }

    @Override
    public boolean deactivateById(int id) throws DAOException {
        boolean affectedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_DEACTIVATE_GENRE_BY_ID)) {
            cstmt.setInt(1, id);
            affectedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_DEACTIVATE_GENRE_BY_ID, ex);
        }
        return affectedRow;
    }

}