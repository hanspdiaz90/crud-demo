package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class GenreQuery {

    private GenreQuery() {}

    public static final String SP_CREATE_GENRE = "call sp_insert_genre(?)";
    public static final String SP_EDIT_GENRE = "call sp_update_genre(?, ?, ?)";
    public static final String SP_FIND_GENRE_BY_ID = "call sp_find_genre_by_id(?)";
    public static final String SP_FIND_ALL_GENRES = "call sp_find_all_genres()";
    public static final String SP_DISABLE_GENRE_BY_ID = "call sp_disable_genre_by_id(?)";

}