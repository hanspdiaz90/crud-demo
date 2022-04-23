package pe.edu.unprg.javaee.inventariolibros.services.impl;

import pe.edu.unprg.javaee.inventariolibros.dao.IBookDAO;
import pe.edu.unprg.javaee.inventariolibros.dao.factory.DAOFactory;
import pe.edu.unprg.javaee.inventariolibros.entities.Author;
import pe.edu.unprg.javaee.inventariolibros.entities.Book;
import pe.edu.unprg.javaee.inventariolibros.entities.Publisher;
import pe.edu.unprg.javaee.inventariolibros.entities.Genre;
import pe.edu.unprg.javaee.inventariolibros.exception.DAOException;
import pe.edu.unprg.javaee.inventariolibros.exception.ServiceException;
import pe.edu.unprg.javaee.inventariolibros.services.IBookService;

import java.util.List;

public class BookServiceImpl implements IBookService {

    private final IBookDAO libroDAO = DAOFactory.getInstance().getBookDAO();

    @Override
    public boolean insert(Book book) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  libroDAO.insert(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public boolean update(Book book) throws ServiceException {
        boolean resultado = false;
        try {
            resultado =  libroDAO.update(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

    @Override
    public Book findById(int id) throws ServiceException {
        Book libro = null;
        try {
            libro = libroDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return libro;
    }

    @Override
    public List<Book> findAllBooks() throws ServiceException {
        List<Book> listaLibros = null;
        try {
            listaLibros = libroDAO.findAllBooks();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaLibros;
    }

    @Override
    public List<Author> findActiveAuthors(String filter) throws ServiceException {
        List<Author> listaAutores = null;
        try {
            listaAutores = libroDAO.findActiveAuthors(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaAutores;
    }

    @Override
    public List<Publisher> findActivePublishers(String filter) throws ServiceException {
        List<Publisher> listaEditoriales = null;
        try {
            listaEditoriales = libroDAO.findActivePublishers(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaEditoriales;
    }

    @Override
    public List<Genre> findActiveGenres(String filter) throws ServiceException {
        List<Genre> listaGeneros = null;
        try {
            listaGeneros = libroDAO.findActiveGenres(filter);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return listaGeneros;
    }

    @Override
    public boolean deactivateById(int id) throws ServiceException {
        boolean resultado = false;
        try {
            resultado = libroDAO.deactivateById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resultado;
    }

}