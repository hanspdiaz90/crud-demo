package pe.edu.unprg.javaee.cruddemo.dao.query;

public class UserQuery {

    private UserQuery() {}

    public static final String SP_INSERT_USER = "{call sp_insert_user(?, ?, ?, ?)}";
    public static final String SP_LOGIN_USER = "{call sp_login_user(?, ?)}";
    public static final String SP_FIND_ALL_MENU_BY_ROLE = "{call sp_find_menu_by_role(?)}";
    public static final String SP_FIND_ALL_MENU_BY_ROLE_RECURSIVE = "{call sp_find_menu_by_role_recursive(?)}";

}