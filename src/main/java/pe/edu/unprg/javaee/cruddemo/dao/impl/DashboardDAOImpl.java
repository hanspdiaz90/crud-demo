package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.DashboardDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.DashboardQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public int countAllAuthors() throws DAOException {
        int count = 0;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(DashboardQuery.COUNT_ALL_AUTHORS)) {
            cstmt.registerOutParameter(1, Types.INTEGER);
            if (cstmt.executeUpdate() > 0) {
                count = cstmt.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + DashboardQuery.COUNT_ALL_AUTHORS, ex);
        }
        return count;
    }

    @Override
    public int countAllBooks() throws DAOException {
        int count = 0;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(DashboardQuery.COUNT_ALL_BOOKS)) {
            cstmt.registerOutParameter(1, Types.INTEGER);
            if (cstmt.executeUpdate() > 0) {
                count = cstmt.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + DashboardQuery.COUNT_ALL_BOOKS, ex);
        }
        return count;
    }

    @Override
    public int countAllGenres() throws DAOException {
        int count = 0;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(DashboardQuery.COUNT_ALL_GENRES)) {
            cstmt.registerOutParameter(1, Types.INTEGER);
            if (cstmt.executeUpdate() > 0) {
                count = cstmt.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + DashboardQuery.COUNT_ALL_GENRES, ex);
        }
        return count;
    }

    @Override
    public int countAllPublishers() throws DAOException {
        int count = 0;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(DashboardQuery.COUNT_ALL_PUBLISHERS)) {
            cstmt.registerOutParameter(1, Types.INTEGER);
            if (cstmt.executeUpdate() > 0) {
                count = cstmt.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + DashboardQuery.COUNT_ALL_PUBLISHERS, ex);
        }
        return count;
    }

}