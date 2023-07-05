package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.GenreDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.GenreQuery;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.utils.Constants;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {

    @Override
    public boolean createGenre(Genre genre) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_CREATE_GENRE)) {
            cstmt.setString(1, genre.getName());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_CREATE_GENRE, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editGenre(Genre genre) throws DAOException {
        boolean rowsUpdated;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_EDIT_GENRE)) {
            cstmt.setString(1, genre.getName());
            cstmt.setBoolean(4, genre.isActive());
            cstmt.setInt(5, genre.getGenreId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_EDIT_GENRE, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Genre findByGenreId(int genreId) throws DAOException {
        Genre genre = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_FIND_GENRE_BY_ID)) {
            cstmt.setInt(1, genreId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setName(rs.getString("name"));
                genre.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_FIND_GENRE_BY_ID, ex);
        }
        return genre;
    }

    @Override
    public List<Genre> findAll() throws DAOException {
        List<Genre> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_FIND_ALL_GENRES)) {
            cstmt.setString(1, Constants.DEFAULT_STRING);
            cstmt.setInt(2, Constants.DEFAULT_INTEGER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setName(rs.getString("name"));
                genre.setActive(rs.getBoolean("is_active"));
                result.add(genre);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_FIND_ALL_GENRES, ex);
        }
        return result;
    }

    @Override
    public boolean disableByGenreId(int genreId) throws DAOException {
        boolean rowsAffected;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(GenreQuery.SP_DISABLE_GENRE_BY_ID)) {
            cstmt.setInt(1, genreId);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + GenreQuery.SP_DISABLE_GENRE_BY_ID, ex);
        }
        return rowsAffected;
    }

}