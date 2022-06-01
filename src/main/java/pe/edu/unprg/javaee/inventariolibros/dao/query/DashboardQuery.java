package pe.edu.unprg.javaee.inventariolibros.dao.query;

public class DashboardQuery {

    private DashboardQuery() {}

    public static final String SP_COUNT_ALL_AUTHORS = "call sp_count_all_authors(?)";
    public static final String SP_COUNT_ALL_BOOKS = "call sp_count_all_books(?)";
    public static final String SP_COUNT_ALL_GENRES = "call sp_count_all_genres(?)";
    public static final String SP_COUNT_ALL_PUBLISHERS = "call sp_count_all_publishers(?)";

}