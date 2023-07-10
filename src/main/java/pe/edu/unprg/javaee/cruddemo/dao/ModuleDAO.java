package pe.edu.unprg.javaee.cruddemo.dao;

import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Module;

import java.util.List;

public interface ModuleDAO {

    boolean createModule(Module module) throws DAOException;
    boolean editModule(Module module) throws DAOException;
    Module findByModuleId(int moduleId) throws DAOException;
    List<Module> findAll() throws DAOException;
    boolean disableByModuleId(int moduleId) throws DAOException;

}