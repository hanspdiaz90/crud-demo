package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.ModuleDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.ModuleQuery;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.utils.Constants;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAOImpl implements ModuleDAO {

    @Override
    public boolean createModule(Module module) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(ModuleQuery.INSERT_MODULE)) {
            cstmt.setString(1, module.getTitle());
            cstmt.setString(2, module.getDescription());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + ModuleQuery.INSERT_MODULE, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editModule(Module module) throws DAOException {
        boolean rowsUpdated;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(ModuleQuery.UPDATE_MODULE)) {
            cstmt.setString(1, module.getTitle());
            cstmt.setString(2, module.getDescription());
            cstmt.setBoolean(3, module.isActive());
            cstmt.setInt(4, module.getModuleId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + ModuleQuery.UPDATE_MODULE, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Module findByModuleId(int moduleId) throws DAOException {
        Module module = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(ModuleQuery.FIND_MODULE_BY_ID)) {
            cstmt.setInt(1, moduleId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                module = new Module();
                module.setModuleId(rs.getInt("module_id"));
                module.setTitle(rs.getString("title"));
                module.setDescription(rs.getString("description"));
                module.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + ModuleQuery.FIND_MODULE_BY_ID, ex);
        }
        return module;
    }

    @Override
    public List<Module> findAll() throws DAOException {
        List<Module> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(ModuleQuery.FIND_ALL_MODULES)) {
            cstmt.setString(1, Constants.NULL_STRING_PARAMETER);
            cstmt.setInt(2, Constants.NOT_INTEGER_PARAMETER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Module module = new Module();
                module.setModuleId(rs.getInt("module_id"));
                module.setTitle(rs.getString("title"));
                module.setDescription(rs.getString("description"));
                module.setActive(rs.getBoolean("is_active"));
                result.add(module);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + ModuleQuery.FIND_ALL_MODULES, ex);
        }
        return result;
    }

    @Override
    public boolean disableByModuleId(int moduleId) throws DAOException {
        boolean rowsAffected;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(ModuleQuery.DISABLE_MODULE_BY_ID)) {
            cstmt.setInt(1, moduleId);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + ModuleQuery.DISABLE_MODULE_BY_ID, ex);
        }
        return rowsAffected;
    }

}