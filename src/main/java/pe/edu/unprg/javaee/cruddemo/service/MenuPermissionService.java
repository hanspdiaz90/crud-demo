package pe.edu.unprg.javaee.cruddemo.service;

import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;

import java.util.List;

public interface MenuPermissionService {

    List<MenuPermission> findAllMenuPermissionByRole(Integer roleId);

}
