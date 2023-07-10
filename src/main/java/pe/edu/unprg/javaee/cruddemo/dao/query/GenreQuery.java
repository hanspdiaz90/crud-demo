package pe.edu.unprg.javaee.cruddemo.dao.query;

public class GenreQuery {

    private GenreQuery() {}

    public static final String INSERT_GENRE = "{call sp_insert_genre(?)}";
    public static final String UPDATE_GENRE = "{call sp_update_genre(?, ?, ?)}";
    public static final String FIND_GENRE_BY_ID = "{call sp_find_by_genre_id(?)}";
    public static final String FIND_ALL_GENRES = "{call sp_find_all_genres(?, ?)}";
    public static final String DISABLE_GENRE_BY_ID = "{call sp_disable_by_genre_id(?)}";

}