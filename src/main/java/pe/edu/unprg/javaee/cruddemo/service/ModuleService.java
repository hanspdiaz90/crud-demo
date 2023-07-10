package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.Module;

import java.util.List;

public interface ModuleService {

    boolean createModule(Module module);
    boolean editModule(Module module);
    Module findByModuleId(int moduleId);
    List<Module> findAll();
    boolean disableByModuleId(int moduleId);

}