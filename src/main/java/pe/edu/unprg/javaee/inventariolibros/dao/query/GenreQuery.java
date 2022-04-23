package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class GenreQuery {

    private GenreQuery() {}

    public static final String SP_INSERT_GENRE = "call sp_insertar_genero(?)";
    public static final String SP_UPDATE_GENRE = "call sp_actualizar_genero(?, ?, ?)";
    public static final String SP_FIND_GENRE_BY_ID = "call sp_buscar_genero(?)";
    public static final String SP_FIND_ALL_GENRES = "call sp_listar_todos_generos()";
    public static final String SP_DEACTIVATE_GENRE_BY_ID = "call sp_desactivar_genero(?)";

}