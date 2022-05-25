package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IBookDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.DAOFactory;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.models.Book;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IBookService;

import java.util.List;

public class BookServiceImpl implements IBookService {

    private final IBookDAO bookDAO = DAOFactory.getInstance().getBookDAO();

    @Override
    public boolean insert(Book book) throws ServiceException {
        boolean result = false;
        try {
            result =  bookDAO.insert(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Book book) throws ServiceException {
        boolean result = false;
        try {
            result =  bookDAO.update(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Book findById(int id) throws ServiceException {
        Book optional = null;
        try {
            optional = bookDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return optional;
    }

    @Override
    public List<Book> findAll() throws ServiceException {
        List<Book> books = null;
        try {
            books = bookDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return books;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) throws ServiceException {
        List<Author> activeAuthors = null;
        try {
            activeAuthors = bookDAO.findActiveAuthors(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return activeAuthors;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) throws ServiceException {
        List<Publisher> activePublishers = null;
        try {
            activePublishers = bookDAO.findActivePublishers(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return activePublishers;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) throws ServiceException {
        List<Genre> activeGenres = null;
        try {
            activeGenres = bookDAO.findActiveGenres(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return activeGenres;
    }

    @Override
    public boolean deactivateById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = bookDAO.deactivateById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}