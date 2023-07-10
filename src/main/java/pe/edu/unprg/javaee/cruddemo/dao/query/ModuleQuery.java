package pe.edu.unprg.javaee.cruddemo.dao.query;

public class ModuleQuery {

    private ModuleQuery() {}

    public static final String INSERT_MODULE = "{call sp_insert_module(?, ?)}";
    public static final String UPDATE_MODULE = "{call sp_update_module(?, ?, ?, ?)}";
    public static final String FIND_MODULE_BY_ID = "{call sp_find_by_module_id(?)}";
    public static final String FIND_ALL_MODULES = "{call sp_find_all_modules(?, ?)}";
    public static final String DISABLE_MODULE_BY_ID = "{call sp_disable_by_module_id(?)}";

}