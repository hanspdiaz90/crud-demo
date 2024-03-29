package pe.edu.unprg.javaee.cruddemo.service.impl;

import pe.edu.unprg.javaee.cruddemo.dao.AuthorDAO;
import pe.edu.unprg.javaee.cruddemo.dao.impl.AuthorDAOImpl;
import pe.edu.unprg.javaee.cruddemo.model.Author;
import pe.edu.unprg.javaee.cruddemo.exception.DAOException;
import pe.edu.unprg.javaee.cruddemo.service.AuthorService;

import java.util.List;
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO = new AuthorDAOImpl();

    @Override
    public boolean createAuthor(Author author) {
        boolean result;
        try {
            result =  this.authorDAO.createAuthor(author);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean editAuthor(Author author) {
        boolean result;
        try {
            result =  this.authorDAO.editAuthor(author);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Author findByAuthorId(int authorId) {
        Author result;
        try {
            result = this.authorDAO.findByAuthorId(authorId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Author> findAll() {
        List<Author> result;
        try {
            result = this.authorDAO.findAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean disableByAuthorId(int authorId) {
        boolean result;
        try {
            result = this.authorDAO.disableByAuthorId(authorId);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /*
    @Override
    public boolean changeStatusById(int id) throws ServiceException {
        boolean result = false;
        try {
            result = this.authorDAO.changeStatusById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }
     */

}