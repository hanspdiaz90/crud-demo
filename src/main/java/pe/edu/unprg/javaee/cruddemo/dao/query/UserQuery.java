package pe.edu.unprg.javaee.cruddemo.dao.query;

public class UserQuery {

    private UserQuery() {}

    public static final String INSERT_USER = "{call sp_insert_user(?, ?, ?, ?)}";
    public static final String LOGIN_USER = "{call sp_login_user(?, ?)}";

}