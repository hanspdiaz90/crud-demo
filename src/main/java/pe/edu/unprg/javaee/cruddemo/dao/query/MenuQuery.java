package pe.edu.unprg.javaee.cruddemo.dao.query;

public class MenuQuery {

    private MenuQuery() {}

    public static final String INSERT_MENU = "{call sp_insert_menu(?, ?, ?, ?, ?, ?)}";
    public static final String UPDATE_MENU = "{call sp_update_menu(?, ?, ?, ?, ?, ?)}";
    public static final String FIND_MENU_BY_ID = "{call sp_find_by_menu_id(?)}";
    public static final String FIND_ALL_MENUS = "{call sp_find_all_menus(?, ?)}";
    public static final String DISABLE_MENU_BY_ID = "{call sp_disable_by_menu_id(?)}";

}