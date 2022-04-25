package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IPublisherDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.PublisherQuery;
import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
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
        boolean insertedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_INSERT_PUBLISHER)) {
            cstmt.setString(1, publisher.getNombre());
            cstmt.setString(2, publisher.getEmail());
            cstmt.setString(3, publisher.getTelefono());
            insertedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_INSERT_PUBLISHER, ex);
        }
        return insertedRow;
    }

    @Override
    public boolean update(Publisher publisher) throws DAOException {
        boolean updatedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_UPDATE_PUBLISHER)) {
            cstmt.setString(1, publisher.getNombre());
            cstmt.setString(2, publisher.getEmail());
            cstmt.setString(3, publisher.getTelefono());
            cstmt.setBoolean(4, publisher.isActivo());
            cstmt.setInt(5, publisher.getId());
            updatedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_UPDATE_PUBLISHER, ex);
        }
        return updatedRow;
    }

    @Override
    public Publisher findById(int id) throws DAOException {
        Publisher optional = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_FIND_PUBLISHER_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                optional = new Publisher(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_FIND_PUBLISHER_BY_ID, ex);
        }
        return optional;
    }

    @Override
    public List<Publisher> findAll() throws DAOException {
        List<Publisher> publishers = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_FIND_ALL_PUBLISHER);
             ResultSet rs = cstmt.executeQuery()) {
            publishers = new ArrayList<>();
            while (rs.next()) {
                Publisher optional = new Publisher(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getBoolean("activo"));
                publishers.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_FIND_ALL_PUBLISHER, ex);
        }
        return publishers;
    }

    @Override
    public boolean deactivateById(int id) throws DAOException {
        boolean affectedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_DEACTIVATE_PUBLISHER_BY_ID)) {
            cstmt.setInt(1, id);
            affectedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_DEACTIVATE_PUBLISHER_BY_ID, ex);
        }
        return affectedRow;
    }

}