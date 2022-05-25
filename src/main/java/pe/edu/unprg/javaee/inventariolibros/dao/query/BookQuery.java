package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class BookQuery {

    private BookQuery() {}

    public static final String SP_INSERT_BOOK = "call sp_insertar_libro(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SP_UPDATE_BOOK = "call sp_actualizar_libro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SP_FIND_BOOK_BY_ID = "call sp_buscar_libro(?)";
    public static final String SP_FIND_ALL_BOOKS = "call sp_listar_todos_libros()";
    public static final String SP_FIND_ACTIVE_AUTHORS = "call sp_listar_autores_activos(?)";
    public static final String SP_FIND_ACTIVE_PUBLISHERS = "call sp_listar_editoriales_activos(?)";
    public static final String SP_FIND_ACTIVE_GENRES = "call sp_listar_generos_activos(?)";
    public static final String SP_DEACTIVATE_BOOK_BY_ID = "call sp_desactivar_libro(?)";

}