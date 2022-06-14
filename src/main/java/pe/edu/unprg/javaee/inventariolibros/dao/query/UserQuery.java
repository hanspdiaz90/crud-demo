package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class UserQuery {

    private UserQuery() {}

    public static final String SP_INSERT_USER = "call sp_insert_user(?, ?, ?, ?)";
    public static final String SP_LOGIN_USER = "call sp_login_user(?, ?)";

}