package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class AuthorQuery {

    private AuthorQuery() {}

    public static final String SP_INSERT_AUTHOR = "call sp_insertar_autor(?, ?, ?)";
    public static final String SP_UPDATE_AUTHOR = "call sp_actualizar_autor(?, ?, ?, ?, ?)";
    public static final String SP_FIND_AUTHOR_BY_ID = "call sp_buscar_autor(?)";
    public static final String SP_FIND_ALL_AUTHORS = "call sp_listar_todos_autores()";
    public static final String SP_DEACTIVATE_AUTHOR_BY_ID = "call sp_desactivar_libro(?)";
    public static final String SP_CHANGE_AUTHOR_STATUS_BY_ID = "call sp_cambiar_estado_autor(?)";

}