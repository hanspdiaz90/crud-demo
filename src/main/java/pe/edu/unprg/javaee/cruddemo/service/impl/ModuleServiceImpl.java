package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.ModuleDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.ModuleDAOImpl;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Module;
import pe.edu.unprg.javaee.cruddemo.service.ModuleService;

import java.util.List;

public class ModuleServiceImpl implements ModuleService {

    private final ModuleDAO moduleDAO = new ModuleDAOImpl();

    @Override
    public boolean createModule(Module module) {
        boolean result;
        try {
            result =  moduleDAO.createModule(module);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editModule(Module module) {
        boolean result;
        try {
            result =  moduleDAO.editModule(module);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Module findByModuleId(int moduleId) {
        Module result;
        try {
            result = moduleDAO.findByModuleId(moduleId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Module> findAll() {
        List<Module> result;
        try {
            result = moduleDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByModuleId(int moduleId) {
        boolean result;
        try {
            result = moduleDAO.disableByModuleId(moduleId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}