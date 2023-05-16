package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.UserDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.UserDAOImpl;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.model.Menu;
import pe.edu.unprg.javaee.cruddemo.model.User;
import pe.edu.unprg.javaee.cruddemo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();

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
            List<Menu> menus = userDAO.findAllMenuByRole(roleType);
            result = this.findAllParents(menus);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private List<Menu> findAllParents(List<Menu> menus) {
        List<Menu> parents = menus
                .stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());
        parents.forEach(parent -> {
            List<Menu> children = menus
                    .stream()
                    .filter(menu -> menu.getParentId() != 0 && menu.getParentId() == parent.getMenuId())
                    .collect(Collectors.toList());
            parent.setChildren(children);
        });
        return parents;
    }

}