package pe.edu.unprg.javaee.cruddemo.dao.query;

public class AuthorQuery {

    private AuthorQuery() {}

    public static final String SP_CREATE_AUTHOR = "{call sp_insert_author(?, ?, ?, ?)}";
    public static final String SP_EDIT_AUTHOR = "{call sp_update_author(?, ?, ?, ?, ?, ?)}";
    public static final String SP_FIND_AUTHOR_BY_ID = "{call sp_find_by_author_id(?)}";
    public static final String SP_FIND_ALL_AUTHORS = "{call sp_find_all_authors()}";
    public static final String SP_DISABLE_AUTHOR_BY_ID = "{call sp_disable_by_author_id(?)}";
    //public static final String SP_CHANGE_AUTHOR_STATUS_BY_ID = "{call sp_change_status_by_author_id(?)}";

}