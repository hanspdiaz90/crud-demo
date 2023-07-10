package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.PublisherDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.PublisherQuery;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.utils.Constants;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAOImpl implements PublisherDAO {

    @Override
    public boolean createPublisher(Publisher publisher) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_CREATE_PUBLISHER)) {
            cstmt.setString(1, publisher.getName());
            cstmt.setString(2, publisher.getEmail());
            cstmt.setString(3, publisher.getAddress());
            cstmt.setString(4, publisher.getPhone());
            cstmt.setString(5, publisher.getCellphone());
            cstmt.setString(6, publisher.getWebSite());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_CREATE_PUBLISHER, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editPublisher(Publisher publisher) throws DAOException {
        boolean rowsUpdated;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_EDIT_PUBLISHER)) {
            cstmt.setString(1, publisher.getName());
            cstmt.setString(2, publisher.getEmail());
            cstmt.setString(3, publisher.getAddress());
            cstmt.setString(4, publisher.getPhone());
            cstmt.setString(5, publisher.getCellphone());
            cstmt.setString(6, publisher.getWebSite());
            cstmt.setBoolean(7, publisher.isActive());
            cstmt.setInt(8, publisher.getPublisherId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_EDIT_PUBLISHER, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Publisher findByPublisherId(int publisherId) throws DAOException {
        Publisher publisher = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_FIND_PUBLISHER_BY_ID)) {
            cstmt.setInt(1, publisherId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                publisher = new Publisher();
                publisher.setPublisherId(rs.getInt("publisher_id"));
                publisher.setName(rs.getString("name"));
                publisher.setEmail(rs.getString("email"));
                publisher.setAddress(rs.getString("address"));
                publisher.setPhone(rs.getString("phone"));
                publisher.setCellphone(rs.getString("cellphone"));
                publisher.setWebSite(rs.getString("web_site"));
                publisher.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_FIND_PUBLISHER_BY_ID, ex);
        }
        return publisher;
    }

    @Override
    public List<Publisher> findAll() throws DAOException {
        List<Publisher> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_FIND_ALL_PUBLISHERS)) {
            cstmt.setString(1, Constants.NULL_STRING_PARAMETER);
            cstmt.setInt(2, Constants.NOT_INTEGER_PARAMETER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setPublisherId(rs.getInt("publisher_id"));
                publisher.setName(rs.getString("name"));
                publisher.setEmail(rs.getString("email"));
                publisher.setAddress(rs.getString("address"));
                publisher.setPhone(rs.getString("phone"));
                publisher.setCellphone(rs.getString("cellphone"));
                publisher.setWebSite(rs.getString("web_site"));
                publisher.setActive(rs.getBoolean("is_active"));
                result.add(publisher);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_FIND_ALL_PUBLISHERS, ex);
        }
        return result;
    }

    @Override
    public boolean disableByPublisherId(int publisherId) throws DAOException {
        boolean rowsAffected;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(PublisherQuery.SP_DISABLE_PUBLISHER_BY_ID)) {
            cstmt.setInt(1, publisherId);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + PublisherQuery.SP_DISABLE_PUBLISHER_BY_ID, ex);
        }
        return rowsAffected;
    }

}