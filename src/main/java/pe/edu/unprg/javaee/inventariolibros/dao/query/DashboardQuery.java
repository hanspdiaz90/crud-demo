package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class DashboardQuery {

    private DashboardQuery() {}

    public static final String SP_COUNT_ALL_AUTHORS = "call sp_contar_todos_autores(?)";
    public static final String SP_COUNT_ALL_BOOKS = "call sp_contar_todos_autores(?)";
    public static final String SP_COUNT_ALL_GENRES = "call sp_contar_todos_autores(?)";
    public static final String SP_COUNT_ALL_PUBLISHERS = "call sp_contar_todos_autores(?)";

}