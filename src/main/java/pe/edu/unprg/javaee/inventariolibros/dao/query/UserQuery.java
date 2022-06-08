package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class UserQuery {

    private UserQuery() {}

    public static final String SP_INSERT_USER = "call sp_insert_user(?, ?, ?, ?)";
    public static final String SP_VALIDATE_USER = "call sp_validate_user(?, ?)";

}