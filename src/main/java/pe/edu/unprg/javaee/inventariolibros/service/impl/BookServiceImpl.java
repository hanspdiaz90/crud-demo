package pe.edu.unprg.javaee.inventariolibros.service.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.BookDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.impl.BookDAOImpl;
import pe.edu.unprg.javaee.inventariolibros.model.Author;
import pe.edu.unprg.javaee.inventariolibros.model.Book;
import pe.edu.unprg.javaee.inventariolibros.model.Publisher;
import pe.edu.unprg.javaee.inventariolibros.model.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO = new BookDAOImpl();

    @Override
    public boolean createBook(Book book) {
        boolean result;
        try {
            result =  bookDAO.createBook(book);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editBook(Book book) {
        boolean result;
        try {
            result =  bookDAO.editBook(book);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Book findByBookId(int bookId) {
        Book result;
        try {
            result = bookDAO.findByBookId(bookId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result;
        try {
            result = bookDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) {
        List<Author> result;
        try {
            result = bookDAO.findActiveAuthors(filter);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) {
        List<Publisher> result;
        try {
            result = bookDAO.findActivePublishers(filter);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) {
        List<Genre> result;
        try {
            result = bookDAO.findActiveGenres(filter);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByBookId(int bookId) {
        boolean result;
        try {
            result = bookDAO.disableByBookId(bookId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}