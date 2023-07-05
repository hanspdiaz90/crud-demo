package pe.edu.unprg.javaee.cruddemo.dao.impl;

import pe.edu.unprg.javaee.cruddemo.dao.BookDAO;
import pe.edu.unprg.javaee.cruddemo.dao.query.BookQuery;
import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.model.Book;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.utils.Constants;
import pe.edu.unprg.javaee.cruddemo.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    @Override
    public boolean createBook(Book book) throws DAOException {
        boolean rowsInserted;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_CREATE_BOOK)) {
            cstmt.setString(1, book.getIsbn());
            cstmt.setString(2, book.getTitle());
            cstmt.setString(3, book.getCoverImage());
            cstmt.setString(4, book.getReview());
            cstmt.setInt(5, book.getYearEdition());
            cstmt.setInt(6, book.getNumberPages());
            cstmt.setDouble(7, book.getPrice());
            cstmt.setInt(8, book.getAuthor().getAuthorId());
            cstmt.setInt(9, book.getPublisher().getPublisherId());
            cstmt.setInt(10, book.getGenre().getGenreId());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_CREATE_BOOK, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean editBook(Book book) throws DAOException {
        boolean rowsUpdated;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_EDIT_BOOK)) {
            cstmt.setString(1, book.getIsbn());
            cstmt.setString(2, book.getTitle());
            cstmt.setString(3, book.getCoverImage());
            cstmt.setString(4, book.getReview());
            cstmt.setInt(5, book.getYearEdition());
            cstmt.setInt(6, book.getNumberPages());
            cstmt.setDouble(7, book.getPrice());
            cstmt.setInt(8, book.getAuthor().getAuthorId());
            cstmt.setInt(9, book.getPublisher().getPublisherId());
            cstmt.setInt(10, book.getGenre().getGenreId());
            cstmt.setBoolean(11, book.isActive());
            cstmt.setInt(12, book.getBookId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_EDIT_BOOK, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Book findByBookId(int bookId) throws DAOException {
        Book book = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_BOOK_BY_ID)) {
            cstmt.setInt(1, bookId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setCoverImage(rs.getString("cover_image"));
                book.setReview(rs.getString("review"));
                book.setYearEdition(rs.getInt("year_edition"));
                book.setNumberPages(rs.getInt("number_pages"));
                book.setPrice(rs.getDouble("price"));
                book.setAuthor(new Author());
                book.getAuthor().setFirstName(rs.getString("first_name"));
                book.getAuthor().setLastName(rs.getString("last_name"));
                book.setPublisher(new Publisher());
                book.getPublisher().setName(rs.getString("publisher"));
                book.setGenre(new Genre());
                book.getGenre().setName(rs.getString("genre"));
                book.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_BOOK_BY_ID, ex);
        }
        return book;
    }

    @Override
    public List<Book> findAll() throws DAOException {
        List<Book> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ALL_BOOKS);
             ResultSet rs = cstmt.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setYearEdition(rs.getInt("year_edition"));
                book.setNumberPages(rs.getInt("number_pages"));
                book.setPrice(rs.getDouble("price"));
                book.setAuthor(new Author());
                book.getAuthor().setFirstName(rs.getString("first_name"));
                book.getAuthor().setLastName(rs.getString("last_name"));
                book.setPublisher(new Publisher());
                book.getPublisher().setName(rs.getString("publisher"));
                book.setGenre(new Genre());
                book.getGenre().setName(rs.getString("genre"));
                book.setActive(rs.getBoolean("is_active"));
                result.add(book);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ALL_BOOKS, ex);
        }
        return result;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) throws DAOException {
        List<Author> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ACTIVE_AUTHORS)) {
            cstmt.setString(1, filter);
            cstmt.setInt(2, Constants.ACTIVE_INTEGER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("author_id"));
                author.setFirstName(rs.getString("first_name"));
                author.setLastName(rs.getString("last_name"));
                author.setActive(rs.getBoolean("is_active"));
                result.add(author);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ACTIVE_AUTHORS, ex);
        }
        return result;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) throws DAOException {
        List<Publisher> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ACTIVE_PUBLISHERS)) {
            cstmt.setString(1, filter);
            cstmt.setInt(2, Constants.ACTIVE_INTEGER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setPublisherId(rs.getInt("publisher_id"));
                publisher.setName(rs.getString("name"));
                publisher.setActive(rs.getBoolean("is_active"));
                result.add(publisher);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ACTIVE_PUBLISHERS, ex);
        }
        return result;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) throws DAOException {
        List<Genre> result;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ACTIVE_GENRES)) {
            cstmt.setString(1, filter);
            cstmt.setInt(2, Constants.ACTIVE_INTEGER);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt("genre_id"));
                genre.setName(rs.getString("name"));
                genre.setActive(rs.getBoolean("is_active"));
                result.add(genre);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ACTIVE_GENRES, ex);
        }
        return result;
    }

    @Override
    public boolean disableByBookId(int bookId) throws DAOException {
        boolean rowsAffected;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_DISABLE_BOOK_BY_ID)) {
            cstmt.setInt(1, bookId);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_DISABLE_BOOK_BY_ID, ex);
        }
        return rowsAffected;
    }

}