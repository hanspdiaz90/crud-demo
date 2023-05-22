package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.MenuDAO;
import pe.edu.unprg.javaee.cruddemo.dao.MenuPermissionDAO;
import pe.edu.unprg.javaee.cruddemo.dao.UserDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.MenuDAOImpl;
import pe.edu.unprg.javaee.cruddemo.dao.impl.MenuPermissionDAOImpl;
import pe.edu.unprg.javaee.cruddemo.dao.impl.UserDAOImpl;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.MenuPermission;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final MenuDAO menuDAO = new MenuDAOImpl();
    private final MenuPermissionDAO menuPermissionDAO = new MenuPermissionDAOImpl();

    @Override
    public boolean createUser(User user) {
        boolean result;
        try {
            result = userDAO.createUser(user);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public User authenticateUser(String email, String password) {
        User result;
        try {
            result = userDAO.authenticateUser(email, password);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Menu> findAllMenuByRole(String roleType) {
        List<Menu> result;
        try {
            List<Menu> menus = menuDAO.findAllMenuByRole(roleType);
            result = this.findAllParentsOld(menus);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<MenuPermission> findAllMenuPermissionByRole(Integer roleId) {
        List<MenuPermission> result;
        try {
            List<MenuPermission> menuPermissions = menuPermissionDAO.findAllMenuPermissionByRole(roleId);
            result = this.findAllParents(menuPermissions);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Menu> findAllParentsOld(List<Menu> menus) {
        List<Menu> parents = menus
                .stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparingInt(Menu::getOrder))
                .collect(Collectors.toList());
        parents.forEach(parent -> {
            List<Menu> children = menus
                    .stream()
                    .filter(menu -> menu.getParentId() != 0 && menu.getParentId() == parent.getMenuId())
                    .collect(Collectors.toList())
                    .stream()
                    .sorted(Comparator.comparingInt(Menu::getOrder))
                    .collect(Collectors.toList());
            parent.setChildren(children);
        });
        return parents;
    }

    private List<MenuPermission> findAllParents(List<MenuPermission> menuPermissions) {
        List<MenuPermission> parents = menuPermissions
                .stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparingInt(MenuPermission::getOrder))
                .collect(Collectors.toList());
        parents.forEach(parent -> {
            List<MenuPermission> children = this.findAllChildrenByParent(parent.getMenuId(), menuPermissions);
            parent.setChildren(children);
        });
        return parents;
    }

    private List<MenuPermission> findAllChildrenByParent(Integer menuId, List<MenuPermission> menuPermissions) {
        List<MenuPermission> children = menuPermissions
                .stream()
                .filter(menu -> menu.getParentId() != 0 && menu.getParentId() == menuId)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparingInt(MenuPermission::getOrder))
                .collect(Collectors.toList());
        children.forEach(child -> {
            child.setChildren(this.findAllChildrenByParent(child.getMenuId(), menuPermissions)
                    .stream()
                    .collect(Collectors.toList()));
        });
        return children;
    }

}