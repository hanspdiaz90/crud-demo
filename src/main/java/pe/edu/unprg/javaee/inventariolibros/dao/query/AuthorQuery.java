package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class AuthorQuery {

    private AuthorQuery() {}

    public static final String SP_INSERT_AUTHOR = "call sp_insert_author(?, ?, ?)";
    public static final String SP_UPDATE_AUTHOR = "call sp_update_author(?, ?, ?, ?, ?)";
    public static final String SP_FIND_AUTHOR_BY_ID = "call sp_find_author_by_id(?)";
    public static final String SP_FIND_ALL_AUTHORS = "call sp_find_all_authors()";
    public static final String SP_DISABLE_AUTHOR_BY_ID = "call sp_disable_author_by_id(?)";
//    public static final String SP_CHANGE_AUTHOR_STATUS_BY_ID = "call sp_change_author_status_by_id(?)";

}