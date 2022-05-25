package pe.edu.unprg.javaee.inventariolibros.dao.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IBookDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.query.BookQuery;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.models.Book;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.utils.DatabaseHandler;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements IBookDAO {

    @Override
    public boolean insert(Book book) throws DAOException {
        boolean insertedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_INSERT_BOOK)) {
            cstmt.setString(1, book.getISBN());
            cstmt.setString(2, book.getTitulo());
            cstmt.setString(3, book.getDescripcion());
            cstmt.setInt(4, book.getExistencias());
            cstmt.setDouble(5, book.getPrecio());
            cstmt.setInt(6, book.getAutor().getId());
            cstmt.setInt(7, book.getEditorial().getId());
            cstmt.setInt(8, book.getGenero().getId());
            insertedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_INSERT_BOOK, ex);
        }
        return insertedRow;
    }

    @Override
    public boolean update(Book book) throws DAOException {
        boolean updatedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_UPDATE_BOOK)) {
            cstmt.setString(1, book.getISBN());
            cstmt.setString(2, book.getTitulo());
            cstmt.setString(3, book.getDescripcion());
            cstmt.setInt(4, book.getExistencias());
            cstmt.setDouble(5, book.getPrecio());
            cstmt.setInt(6, book.getAutor().getId());
            cstmt.setInt(7, book.getEditorial().getId());
            cstmt.setInt(8, book.getGenero().getId());
            cstmt.setBoolean(9, book.isActivo());
            cstmt.setInt(10, book.getId());
            updatedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_UPDATE_BOOK, ex);
        }
        return updatedRow;
    }

    @Override
    public Book findById(int id) throws DAOException {
        Book optional = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_BOOK_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                optional = new Book(rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getInt("existencias"),
                        rs.getDouble("precio"),
                        new Author(),
                        new Publisher(),
                        new Genre(),
                        rs.getBoolean("activo"));
                optional.getAutor().setNombres(rs.getString("nombres"));
                optional.getAutor().setApellidos(rs.getString("apellidos"));
                optional.getEditorial().setNombre(rs.getString("editorial"));
                optional.getGenero().setNombre(rs.getString("genero"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_BOOK_BY_ID, ex);
        }
        return optional;
    }

    @Override
    public List<Book> findAll() throws DAOException {
        List<Book> books = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ALL_BOOKS);
             ResultSet rs = cstmt.executeQuery()) {
            books = new ArrayList<>();
            while (rs.next()) {
                Book optional = new Book(rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getInt("existencias"),
                        rs.getDouble("precio"),
                        new Author(),
                        new Publisher(),
                        new Genre(),
                        rs.getBoolean("activo"));
                optional.getAutor().setNombres(rs.getString("nombres"));
                optional.getAutor().setApellidos(rs.getString("apellidos"));
                optional.getEditorial().setNombre(rs.getString("editorial"));
                optional.getGenero().setNombre(rs.getString("genero"));
                books.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ALL_BOOKS, ex);
        }
        return books;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) throws DAOException {
        List<Author> authors = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ACTIVE_AUTHORS)) {
            cstmt.setString(1, filter);
            ResultSet rs = cstmt.executeQuery();
            authors = new ArrayList<>();
            while (rs.next()) {
                Author optional = new Author();
                optional.setId(rs.getInt("id"));
                optional.setNombres(rs.getString("nombres"));
                optional.setApellidos(rs.getString("apellidos"));
                authors.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ACTIVE_AUTHORS, ex);
        }
        return authors;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) throws DAOException {
        List<Publisher> publishers = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ACTIVE_PUBLISHERS)) {
            cstmt.setString(1, filter);
            ResultSet rs = cstmt.executeQuery();
            publishers = new ArrayList<>();
            while (rs.next()) {
                Publisher optional = new Publisher();
                optional.setId(rs.getInt("id"));
                optional.setNombre(rs.getString("nombre"));
                publishers.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ACTIVE_PUBLISHERS, ex);
        }
        return publishers;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) throws DAOException {
        List<Genre> genres = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ACTIVE_GENRES)) {
            cstmt.setString(1, filter);
            ResultSet rs = cstmt.executeQuery();
            genres = new ArrayList<>();
            while (rs.next()) {
                Genre optional = new Genre();
                optional.setId(rs.getInt("id"));
                optional.setNombre(rs.getString("nombre"));
                genres.add(optional);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ACTIVE_GENRES, ex);
        }
        return genres;
    }

    @Override
    public boolean deactivateById(int id) throws DAOException {
        boolean affectedRow = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_DEACTIVATE_BOOK_BY_ID)) {
            cstmt.setInt(1, id);
            affectedRow = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_DEACTIVATE_BOOK_BY_ID, ex);
        }
        return affectedRow;
    }

}