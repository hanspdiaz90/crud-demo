package pe.edu.unprg.javaee.cruddemo.dao.query;

public class AuthorQuery {

    private AuthorQuery() {}

    public static final String INSERT_AUTHOR = "{call sp_insert_author(?, ?, ?, ?)}";
    public static final String UPDATE_AUTHOR = "{call sp_update_author(?, ?, ?, ?, ?, ?)}";
    public static final String FIND_AUTHOR_BY_ID = "{call sp_find_by_author_id(?)}";
    public static final String FIND_ALL_AUTHORS = "{call sp_find_all_authors(?, ?)}";
    public static final String DISABLE_AUTHOR_BY_ID = "{call sp_disable_by_author_id(?)}";
    //public static final String SP_CHANGE_AUTHOR_STATUS_BY_ID = "{call sp_change_status_by_author_id(?)}";

}