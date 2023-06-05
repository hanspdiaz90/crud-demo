package pe.edu.unprg.javaee.cruddemo.dao.query;

public class MenuPermissionQuery {

    private MenuPermissionQuery() {}

    public static final String SP_FIND_ALL_MENU_BY_ROLE = "{call sp_find_menu_by_role(?)}";

}