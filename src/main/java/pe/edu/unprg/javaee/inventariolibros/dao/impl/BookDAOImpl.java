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
        boolean rowsInserted = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_INSERT_BOOK)) {
            cstmt.setString(1, book.getIsbn());
            cstmt.setString(2, book.getTitulo());
            cstmt.setString(3, book.getPortada());
            cstmt.setString(4, book.getResenia());
            cstmt.setInt(5, book.getAnioEdicion());
            cstmt.setInt(6, book.getNroPaginas());
            cstmt.setInt(7, book.getEjemplares());
            cstmt.setDouble(8, book.getPrecio());
            cstmt.setInt(9, book.getAutor().getId());
            cstmt.setInt(10, book.getEditorial().getId());
            cstmt.setInt(11, book.getGenero().getId());
            rowsInserted = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_INSERT_BOOK, ex);
        }
        return rowsInserted;
    }

    @Override
    public boolean update(Book book) throws DAOException {
        boolean rowsUpdated = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_UPDATE_BOOK)) {
            cstmt.setString(1, book.getIsbn());
            cstmt.setString(2, book.getTitulo());
            cstmt.setString(3, book.getPortada());
            cstmt.setString(4, book.getResenia());
            cstmt.setInt(5, book.getAnioEdicion());
            cstmt.setInt(6, book.getNroPaginas());
            cstmt.setInt(7, book.getEjemplares());
            cstmt.setDouble(8, book.getPrecio());
            cstmt.setInt(9, book.getAutor().getId());
            cstmt.setInt(10, book.getEditorial().getId());
            cstmt.setInt(11, book.getGenero().getId());
            cstmt.setBoolean(12, book.isActivo());
            cstmt.setInt(13, book.getId());
            rowsUpdated = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_UPDATE_BOOK, ex);
        }
        return rowsUpdated;
    }

    @Override
    public Book findById(int id) throws DAOException {
        Book book = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_BOOK_BY_ID)) {
            cstmt.setInt(1, id);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitulo(rs.getString("titulo"));
                book.setTitulo(rs.getString("portada"));
                book.setResenia(rs.getString("sinopsis"));
                book.setEjemplares(rs.getInt("anio_publicacion"));
                book.setEjemplares(rs.getInt("nro_paginas"));
                book.setEjemplares(rs.getInt("ejemplares"));
                book.setPrecio(rs.getDouble("precio"));
                book.setAutor(new Author());
                book.getAutor().setNombres(rs.getString("nombres"));
                book.getAutor().setApellidos(rs.getString("apellidos"));
                book.setEditorial(new Publisher());
                book.getEditorial().setNombre(rs.getString("editorial"));
                book.setGenero(new Genre());
                book.getGenero().setNombre(rs.getString("genero"));
                book.setActivo(rs.getBoolean("activo"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_BOOK_BY_ID, ex);
        }
        return book;
    }

    @Override
    public List<Book> findAll() throws DAOException {
        List<Book> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_FIND_ALL_BOOKS);
             ResultSet rs = cstmt.executeQuery()) {
            result = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitulo(rs.getString("titulo"));
                book.setAnioEdicion(rs.getInt("anio_publicacion"));
                book.setNroPaginas(rs.getInt("nro_paginas"));
                book.setEjemplares(rs.getInt("ejemplares"));
                book.setPrecio(rs.getDouble("precio"));
                book.setAutor(new Author());
                book.getAutor().setNombres(rs.getString("nombres"));
                book.getAutor().setApellidos(rs.getString("apellidos"));
                book.setEditorial(new Publisher());
                book.getEditorial().setNombre(rs.getString("editorial"));
                book.setGenero(new Genre());
                book.getGenero().setNombre(rs.getString("genero"));
                book.setActivo(rs.getBoolean("activo"));
                result.add(book);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_FIND_ALL_BOOKS, ex);
        }
        return result;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) throws DAOException {
        List<Author> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_SEARCH_ACTIVE_AUTHORS)) {
            cstmt.setString(1, filter);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setNombres(rs.getString("nombres"));
                author.setApellidos(rs.getString("apellidos"));
                author.setActivo(rs.getBoolean("activo"));
                result.add(author);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_SEARCH_ACTIVE_AUTHORS, ex);
        }
        return result;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) throws DAOException {
        List<Publisher> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_SEARCH_ACTIVE_PUBLISHERS)) {
            cstmt.setString(1, filter);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(rs.getInt("id"));
                publisher.setNombre(rs.getString("nombre"));
                publisher.setActivo(rs.getBoolean("activo"));
                result.add(publisher);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_SEARCH_ACTIVE_PUBLISHERS, ex);
        }
        return result;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) throws DAOException {
        List<Genre> result = null;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_SEARCH_ACTIVE_GENRES)) {
            cstmt.setString(1, filter);
            ResultSet rs = cstmt.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getInt("id"));
                genre.setNombre(rs.getString("nombre"));
                genre.setActivo(rs.getBoolean("activo"));
                result.add(genre);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_SEARCH_ACTIVE_GENRES, ex);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws DAOException {
        boolean rowsAffected = false;
        try (Connection conn = DatabaseHandler.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall(BookQuery.SP_DISABLE_BOOK_BY_ID)) {
            cstmt.setInt(1, id);
            rowsAffected = cstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DAOException("Error al ejecutar la consulta: " + BookQuery.SP_DISABLE_BOOK_BY_ID, ex);
        }
        return rowsAffected;
    }

}