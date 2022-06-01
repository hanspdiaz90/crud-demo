package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IGenreDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.GenreQuery;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
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
        boolean rowsInserted = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_INSERT_GENRE)) {
            cstmt.setString(1, genre.getNombre());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_INSERT_GENRE, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean update(Genre genre) throws DAOException {
        boolean rowsUpdated = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_UPDATE_GENRE)) {
            cstmt.setString(1, genre.getNombre());
            cstmt.setBoolean(4, genre.isActivo());
            cstmt.setInt(5, genre.getId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_UPDATE_GENRE, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Genre findById(int id) throws DAOException {
        Genre genre = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_FIND_GENRE_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setNombre(rs.getString("nombre"));
                genre.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_FIND_GENRE_BY_ID, ex);
        }
        return genre;
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_FIND_ALL_GENRES);
             ResultSet rs = cstmt.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setNombre(rs.getString("nombre"));
                genre.setActivo(rs.getBoolean("activo"));
                result.add(genre);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_FIND_ALL_GENRES, ex);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws DAOException {
        boolean rowsAffected = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_DISABLE_GENRE_BY_ID)) {
            cstmt.setInt(1, id);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_DISABLE_GENRE_BY_ID, ex);
        }
        return rowsAffected;
    }

}