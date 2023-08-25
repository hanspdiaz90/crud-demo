package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.BookDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.BookDAOImpl;
import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.model.Book;
import pe.edu.unprg.javaee.cruddemo.model.Publisher;
import pe.edu.unprg.javaee.cruddemo.model.Genre;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO = new BookDAOImpl();

    @Override
    public boolean createBook(Book book) {
        boolean result;
        try {
            result =  this.bookDAO.createBook(book);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editBook(Book book) {
        boolean result;
        try {
            result =  this.bookDAO.editBook(book);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Book findByBookId(int bookId) {
        Book result;
        try {
            result = this.bookDAO.findByBookId(bookId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result;
        try {
            result = this.bookDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) {
        List<Author> result;
        try {
            result = this.bookDAO.findActiveAuthors(filter);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) {
        List<Publisher> result;
        try {
            result = this.bookDAO.findActivePublishers(filter);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) {
        List<Genre> result;
        try {
            result = this.bookDAO.findActiveGenres(filter);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByBookId(int bookId) {
        boolean result;
        try {
            result = this.bookDAO.disableByBookId(bookId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}