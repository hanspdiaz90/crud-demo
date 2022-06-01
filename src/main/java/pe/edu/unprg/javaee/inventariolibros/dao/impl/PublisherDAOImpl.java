package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IPublisherDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.PublisherQuery;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAOImpl implements IPublisherDAO {

    @Override
    public boolean insert(Publisher publisher) throws DAOException {
        boolean rowsInserted = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_INSERT_PUBLISHER)) {
            cstmt.setString(1, publisher.getNombre());
            cstmt.setString(2, publisher.getEmail());
            cstmt.setString(3, publisher.getTelefono());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_INSERT_PUBLISHER, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean update(Publisher publisher) throws DAOException {
        boolean rowsUpdated = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_UPDATE_PUBLISHER)) {
            cstmt.setString(1, publisher.getNombre());
            cstmt.setString(2, publisher.getEmail());
            cstmt.setString(3, publisher.getTelefono());
            cstmt.setBoolean(4, publisher.isActivo());
            cstmt.setInt(5, publisher.getId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_UPDATE_PUBLISHER, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Publisher findById(int id) throws DAOException {
        Publisher publisher = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_FIND_PUBLISHER_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                publisher = new Publisher();
                publisher.setId(rs.getInt("id"));
                publisher.setNombre(rs.getString("nombre"));
                publisher.setEmail(rs.getString("email"));
                publisher.setTelefono(rs.getString("telefono"));
                publisher.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_FIND_PUBLISHER_BY_ID, ex);
        }
        return publisher;
    }

    @Override
    public List<Publisher> findAll() throws DAOException {
        List<Publisher> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_FIND_ALL_PUBLISHERS);
             ResultSet rs = cstmt.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(rs.getInt("id"));
                publisher.setNombre(rs.getString("nombre"));
                publisher.setEmail(rs.getString("email"));
                publisher.setTelefono(rs.getString("telefono"));
                publisher.setActivo(rs.getBoolean("activo"));
                result.add(publisher);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_FIND_ALL_PUBLISHERS, ex);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws DAOException {
        boolean rowsAffected = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_DISABLE_PUBLISHER_BY_ID)) {
            cstmt.setInt(1, id);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_DISABLE_PUBLISHER_BY_ID, ex);
        }
        return rowsAffected;
    }

}