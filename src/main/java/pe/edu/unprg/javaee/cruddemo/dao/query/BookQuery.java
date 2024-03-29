package pe.edu.unprg.javaee.cruddemo.dao.query;

public class BookQuery {

    private BookQuery() {}

    public static final String INSERT_BOOK = "{call sp_insert_book(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    public static final String UPDATE_BOOK = "{call sp_update_book(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    public static final String FIND_BOOK_BY_ID = "{call sp_find_by_book_id(?)}";
    public static final String FIND_ALL_BOOKS = "{call sp_find_all_books(?, ?, ?, ?, ?, ?)}";
    public static final String FIND_ACTIVE_AUTHORS = "{call sp_find_all_authors(?, ?)}";
    public static final String FIND_ACTIVE_PUBLISHERS = "{call sp_find_all_publishers(?, ?)}";
    public static final String FIND_ACTIVE_GENRES = "{call sp_find_all_genres(?, ?)}";
    public static final String DISABLE_BOOK_BY_ID = "{call sp_disable_by_book_id(?)}";

}