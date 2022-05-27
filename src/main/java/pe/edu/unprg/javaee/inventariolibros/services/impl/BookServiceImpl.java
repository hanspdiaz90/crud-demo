package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IBookDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.LibraryDAOFactory;
import pe.edu.unprg.javaee.inventariolibros.models.Author;
import pe.edu.unprg.javaee.inventariolibros.models.Book;
import pe.edu.unprg.javaee.inventariolibros.models.Publisher;
import pe.edu.unprg.javaee.inventariolibros.models.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IBookService;

import java.util.List;

public class BookServiceImpl implements IBookService {

    private final IBookDAO bookDAO = LibraryDAOFactory.getInstance().getBookDAO();

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
        Book result = null;
        try {
            result = bookDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() throws ServiceException {
        List<Book> result = null;
        try {
            result = bookDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) throws ServiceException {
        List<Author> result = null;
        try {
            result = bookDAO.findActiveAuthors(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) throws ServiceException {
        List<Publisher> result = null;
        try {
            result = bookDAO.findActivePublishers(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) throws ServiceException {
        List<Genre> result = null;
        try {
            result = bookDAO.findActiveGenres(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean disableById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = bookDAO.disableById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}